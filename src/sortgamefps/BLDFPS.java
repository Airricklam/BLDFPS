package sortgamefps;

import java.io.*;
import java.util.*;

public class BLDFPS {
	
	private static int AMDCPU = 8;
	private static int IntelCPU = 5;
	private static int numOfGPU = 9;
	
	public static void main (String[] args) throws Exception {
		
		int[][] AMD1080 = storeFPS("AMD1080.txt", AMDCPU);
		int[][] AMD1440 = storeFPS("AMD1440.txt", AMDCPU);
		int[][] INTEL1080 = storeFPS("INTEL1080.txt", IntelCPU);
		int[][] INTEL1440 = storeFPS("INTEL1440.txt", IntelCPU);
		
		boolean A1080 = false, A1440 = false, I1080 = false, I1440 = false;
		
		while (!(A1080 && A1440 && I1080 && I1440)) {
			AMD1080 = organizeFPS(AMD1080, AMDCPU);
			AMD1440 = organizeFPS(AMD1440, AMDCPU);
			INTEL1080 = organizeFPS(INTEL1080, IntelCPU);
			INTEL1440 = organizeFPS(INTEL1440, IntelCPU);
			
			if (isArraySorted(AMD1080)) {
				A1080 = true;
			}
			if (isArraySorted(AMD1440)) {
				A1440 = true;
			}
			if (isArraySorted(INTEL1080)) {
				I1080 = true;
			}
			if (isArraySorted(INTEL1440)) {
				I1440 = true;
			}
		}	
		display2DArray(AMD1080);
		display2DArray(INTEL1080);
		System.out.println("-------------------------------------");
		display2DArray(AMD1440);
		display2DArray(INTEL1440);
//		createNewFPS("New 1080.txt", AMD1080, INTEL1080);
//		createNewFPS("New 1440.txt", AMD1440, INTEL1440);
	}
	
	public static int[][] storeFPS(String fileName, int numOfCPU) throws Exception {
		int[][] FPS = new int[numOfCPU][numOfGPU];
		File file = new File (fileName);
		BufferedReader br = new BufferedReader(new FileReader(file));		
		String data;
		for (int x = 0; x < FPS.length; x++) {
			for(int y = 0; y < FPS[x].length; y++) {
				data = br.readLine();
				FPS[x][y] = Integer.parseInt(data);
			}
		}
		return FPS;
	}
	
	public static int[][] organizeFPS(int[][] FPS, int numOfCPU) {
		for (int x = 0; x < FPS.length; x++) {
			Arrays.sort(FPS[x]);
		}
		int [] temp;
		for (int x = 0; x < FPS[0].length; x++) {
			temp = new int[numOfCPU];
			for (int i = 0; i < numOfCPU; i++) {
				temp[i] = FPS[i][x];
			}
			Arrays.sort(temp);
			for (int y = 0; y < FPS.length; y++) {
				FPS[y][x] = temp[y];
			}
			
		}
		for (int x = (FPS.length-1); x >= 0; x--) {
			for (int y = (FPS[x].length-1); y >= 0; y--) {
				if (x > 0){
					if (FPS[x][y] == FPS[x-1][y]){
						FPS[x-1][y] = FPS[x-1][y] - 1;
					}
				}
				if (y > 0) {
					if (FPS[x][y] == FPS[x][y-1]){
						FPS[x][y-1] = FPS[x][y-1] - 1;
					}
				}
			}
		}
		return FPS;
	}
	
	public static boolean isArraySorted(int[][] FPS) {
		for (int x = 0; x < FPS.length; x++) {
			for (int y = 0; y < FPS[x].length; y++) {
				if (x != (FPS.length - 1)){
					if (FPS[x][y] >= FPS[x+1][y]){
						return false;
					}
				}
				if (y != (FPS[x].length - 1)) {
					if (FPS[x][y] >= FPS[x][y+1]){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static void display2DArray(int[][] FPS) {
		for (int x = 0; x < FPS.length; x++) {
			for (int y = 0; y < FPS[x].length; y++) {
				System.out.println(FPS[x][y]);
			}
		}
	}
	
	public static void createNewFPS(String str, int[][] FPS1, int[][] FPS2) throws Exception{
		PrintWriter writer = new PrintWriter(str, "UTF-8");
		for (int x = 0; x < FPS1.length; x++) {
			for (int y = 0; y < FPS1[x].length; y++) {
				writer.println(FPS1[x][y]);
			}
		}
		for (int x = 0; x < FPS2.length; x++) {
			for (int y = 0; y < FPS2[x].length; y++) {
				writer.println(FPS2[x][y]);
			}
		}
		writer.close();
	}
}