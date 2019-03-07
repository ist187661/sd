package example.grpc.server;

/* these are generated by the hello-world-server contract */
import example.grpc.HelloWorld;
import example.grpc.HelloWorldServiceGrpc;

import io.grpc.stub.StreamObserver;

public class TTT {
	char board[][] = {
			{ '1', '2', '3' }, /* Initial values are reference numbers */
			{ '4', '5', '6' }, /* used to select a vacant square for */
			{ '7', '8', '9' } /* a turn. */
	};
	int nextPlayer = 0;
	int numPlays = 0;

	public void currentBoard(
		TTTT.BoardRequest request,
		StreamObserver<TTTT.BoardResponse> responseObserver
	) {
		System.out.println(request);

		String s = "\n\n " + board[0][0] + " | " + board[0][1] + " | " + board[0][2] + " " + "\n---+---+---\n "
				+ board[1][0] + " | " + board[1][1] + " | " + board[1][2] + " " + "\n---+---+---\n " + board[2][0]
				+ " | " + board[2][1] + " | " + board[2][2] + " \n";

		// You must use a builder to construct a new Protobuffer object
		HelloWorld.HelloResponse response = HelloWorld.HelloResponse.newBuilder()
				.setCurrentBoard(s).build();

		// Use responseObserver to send a single response back
		responseObserver.onNext(response);

		// When you are done, you must call onCompleted
		responseObserver.onCompleted();
	}

	public void play(
		TTTT.PlayRequest request,
		StreamObserver<TTTT.PlayResponse> responseObserver
	) {
		System.out.println(request);

		boolean val;
		
		if (!(row >= 0 && row < 3 && column >= 0 && column < 3))
			val = false;
		if (board[row][column] > '9')
			val = false;
		if (player != nextPlayer)
			val = false;

		if (numPlays == 9)
			val = false;

		board[row][column] = (player == 1) ? 'X'
				: 'O'; /* Insert player symbol */
		nextPlayer = (nextPlayer + 1) % 2;
		numPlays++;

		val = true;

		// You must use a builder to construct a new Protobuffer object
		TTTT.PlayResponse response = TTTT.PlayResponse.newBuilder()
			.setPlay(val).build();

		// Use responseObserver to send a single response back
		responseObserver.onNext(response);

		// When you are done, you must call onCompleted
		responseObserver.onCompleted();
	}

	public void checkWinner(
		TTTT.WinnerRequest request,
		StreamObserver<TTTT.WinnerResponse> responseObserver
	) {
		System.out.println(request);

		int i;
		/* Check for a winning line - diagonals first */
		if ((board[0][0] == board[1][1] && board[0][0] == board[2][2])
				|| (board[0][2] == board[1][1] && board[0][2] == board[2][0])) {
			if (board[1][1] == 'X')
				return 1;
			else
				return 0;
		} else {
			/* Check rows and columns for a winning line */
			for (i = 0; i <= 2; i++) {
				if ((board[i][0] == board[i][1] && board[i][0] == board[i][2])) {
					if (board[i][0] == 'X')
						return 1;
					else
						return 0;
				}

				if ((board[0][i] == board[1][i] && board[0][i] == board[2][i])) {
					if (board[0][i] == 'X')
						return 1;
					else
						return 0;
				}
			}
		}

		int val;
		if (numPlays == 9)
			val 2; /* A draw! */
		else
			val -1; /* Game is not over yet */

		// You must use a builder to construct a new Protobuffer object
		TTTT.WinnerResponse response = TTTT.WinnerResponse.newBuilder()
			.setWinner(val).build();

		// Use responseObserver to send a single response back
		responseObserver.onNext(response);

		// When you are done, you must call onCompleted
		responseObserver.onCompleted();
	}

}
