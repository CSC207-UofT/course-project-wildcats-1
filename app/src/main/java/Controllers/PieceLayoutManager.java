package Controllers;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import com.wildcats.ultimatechess.R;

public class PieceLayoutManager{

    private ImageView[][] board = new ImageView[8][8];
    private ImageView image;
    final char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    final char[] nums = {'1', '2', '3', '4', '5', '6', '7', '8'};
    int id;
    private Activity activity;

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
                this.board[i][k]=activity.findViewById(this.id);
            }
        }
    }
    public ImageView getImageAt(int col, int row){
        return this.board[col][row];
    }
    public void editImageAt(int col, int row, int newImage) {
        this.board[col][row].setImageResource(newImage);
    }
}
