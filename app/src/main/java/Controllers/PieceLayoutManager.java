package Controllers;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import com.wildcats.ultimatechess.R;

import java.util.Arrays;

public class PieceLayoutManager{

    private ImageView[][] board = new ImageView[8][8];
    private ImageView image;
    final char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    final char[] nums = {'1', '2', '3', '4', '5', '6', '7', '8'};
    int id;
    private Activity activity;
    private int[] clicked = new int[2];

    public PieceLayoutManager(Context context){
        this.activity = (Activity)context;
        this.populate(context);
        this.board[3][4].setImageResource(R.drawable.bishop_white);
    }
    private void populate(Context context){
        for (int i=0;i<8;i++) {
            for (int k=0;k<8;k++){
                this.id =context.getResources().getIdentifier(""+this.letters[i]+this.nums[7-k],
                        "id",
                        context.getPackageName());
                this.board[i][k]=this.activity.findViewById(this.id);
            }
        }
    }
    public ImageView getImageAt(int col, int row){
        return this.board[col][row];
    }
    public void editImageAt(int col, int row, int newImage) {
        this.board[col][row].setImageResource(newImage);
    }
    public void setClicked(MoveBufferInterface moveBuffer){
        String move = moveBuffer.getClick1();
        if (this.clicked[0] != 0){
            this.board[this.clicked[0]][this.clicked[1]].setBackgroundResource(0);}

        if (move != null) {
            int col = Arrays.binarySearch(this.letters, move.charAt(0));
            int row = 7-Arrays.binarySearch(this.nums, move.charAt(1));
            this.board[col][row].setBackgroundResource(R.drawable.clicked_bg);
            this.clicked[0]= col;
            this.clicked[1]= row;

        }



    }
}
