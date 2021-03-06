package _210406.boj1103;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * [골드 2] 게임
 * 1. 결과 : 성공
 * 2. 시간 복잡도 : O(NM)
 * 	- 이유
 * 	(1) 범위 : N(~50), M(~50)
 * 	(2) 접근
 * 		- dfs로 탐색
 * 		조건(1) 사이클 발생 여부 
 * 				-> boolean[][] 배열로 방문처리
 * 		조건(2) 상하좌우로 이동할 경우, 중복 방문 처리
 * 				-> dp로 해당 위치에 도착했을 때의 cnt를 저장
 * 		조건(3) 게임 끝나는 조건
 * 				-> MAX값 추출
 *  (2) DFS 시간 복잡도
 *  	하나의 정점에서 DFS를 돌리면, V개의 정점을 방문함.
 *  	이 문제에 경우, 모든 맵을 전부 돌 수 있움
 *  	그래서 여기에서 V는 N*M이라 생각함.
 *  	상하좌우로 도니까 4*N*M. 
 *  	상수는 제거해서 NM이 나온다. (몰랐는데 친절한 사람들이 알려줌)
 *  
 * 3. 후기
 * - 처음에 시작지점을 임의로 정해야하는 줄 알고 동공강진함.
 * - 시간 복잡도는 여전히 어렵다...
 */
public class Main_ja {
	static int[][] dt = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
	static int HOLE = -1;
	static int N, M;

	public static int[] inputToIntArr(String str, String separator) {
		return Arrays.stream(str.split(separator)).mapToInt(Integer::parseInt).toArray();
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] input = inputToIntArr(br.readLine(), " ");
		N = input[0];
		M = input[1];

		int[][] move = new int[N][M];
		for (int[] m : move) {
			char[] c = br.readLine().toCharArray();
			for (int j = 0; j < M; j++) {
				m[j] = c[j] == 'H' ? HOLE : c[j] - '0'; // 구멍인 경우 -1 저장
			}
		}
		int[][] dp = new int[N][M];
		dfs(move, new boolean[N][M], dp, new Node(0, 0));
		System.out.println(dp[0][0]);

	}

	static int dfs(int[][] move, boolean[][] visited, int[][] dp, Node cur) {
		if (visited[cur.r][cur.c]) { // 방문한 곳 또 방문하면, 사이클 발생 (무한)
			System.out.println(-1);
			System.exit(0);
		}
		if (dp[cur.r][cur.c] > 0)  // 전에 탐색했던 곳 방문이면 저장한 값 return
			return dp[cur.r][cur.c];
		
		int cnt = 1;
		visited[cur.r][cur.c] = true;
		
		for (int d = 0; d < 4; d++) {
			int r = cur.r + dt[d][0] * move[cur.r][cur.c];
			int c = cur.c + dt[d][1] * move[cur.r][cur.c];
			if (r < 0 || c < 0 || r >= N || c >= M || move[r][c] == HOLE)  // 게임 끝 조건
				continue;

			cnt = Math.max(cnt, dfs(move, visited, dp, new Node(r, c))+1); // 다음 지점으로 이동
		}
		
		visited[cur.r][cur.c] = false;
		return dp[cur.r][cur.c] = cnt;
	}

	static class Node {
		int r, c;

		public Node(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}

}
