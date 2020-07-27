package chess.com.engine;

public enum Alliance {
	WHITE{
		@Override
		int getDirection() {
			return -1;
		}
		
		@Override
		boolean isWhite() {
			return true;
		}
		
		@Override
		boolean isBlack() {
			return false;
		}
	},
	
	BLACK{
		int getDirection() {
			return 1;
		}
		
		@Override
		boolean isWhite() {
			return false;
		}
		
		@Override
		boolean isBlack() {
			return true;
		}
	};
	
	public abstract int getDirection();
	public abstract boolean isWhite();
	public abstract boolean isBlack();
}
