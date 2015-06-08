package course.labs.modernartui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.SeekBar;
import android.graphics.Color;
import android.content.Intent;
import android.net.Uri;

public class ModernArtUIActivity extends Activity {

    private static TextView mRedView;
    private static TextView mGreenView;
    private static TextView mBlueView;
    private static TextView mYellowView;
    private static SeekBar mSeekBar;

    private DialogFragment mDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mRedView = (TextView) findViewById(R.id.RedView);
        mGreenView = (TextView) findViewById(R.id.GreenView);
        mBlueView = (TextView) findViewById(R.id.BlueView);
        mYellowView = (TextView) findViewById(R.id.YellowView);
        mSeekBar = (SeekBar) findViewById(R.id.SeekBar);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeColors(progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    // Create Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    // Process clicks on Options Menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.more_info:
                // Create a new InfoDialogFragment
                mDialog = InfoDialogFragment.newInstance();

                // Show InfoDialogFragment
                mDialog.show(getFragmentManager(), "More Information");

                return true;
            default:
                return false;
        }
    }

    // Abort or show MoreInfo based on value of shouldContinue
    private void continueMoreInfo(boolean shouldContinue) {
        if (shouldContinue) {
            // Launch browser
            final String URL = "http://www.moma.org";

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
            startActivity(browserIntent);
        } else {
            // Dismiss dialog
            mDialog.dismiss();
        }
    }


    private void changeColors(int progress) {
        int color = (int) (255f * (201-progress)/201);
        mRedView.setBackgroundColor(Color.argb(255, color, 0, 0));
        mGreenView.setBackgroundColor(Color.argb(255,0, color,0));
        mBlueView.setBackgroundColor(Color.argb(255, 0, 0, color));
        mYellowView.setBackgroundColor(Color.argb(255,color,color,0));
    }

    // Class that creates the InfoDialog
    public static class InfoDialogFragment extends DialogFragment {

        public static InfoDialogFragment newInstance() {
            return new InfoDialogFragment();
        }

        // Build InfoDialog using AlertDialog.Builder
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setTitle("More Information")
                    .setMessage("Inspired by the works of artists such as " +
                            "Piet Mondrian and Ben Nicholson. \n\n" +
                            "Click below to learn more!")
                            // User cannot dismiss dialog by hitting back button
                    .setCancelable(false)

                            // Set up "Visit MOMA" Button
                    .setNegativeButton("Visit MOMA",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    ((ModernArtUIActivity) getActivity())
                                            .continueMoreInfo(true);
                                }
                            })

                            // Set up "Not Now" Button
                    .setPositiveButton("Not Now",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        final DialogInterface dialog, int id) {
                                    ((ModernArtUIActivity) getActivity())
                                            .continueMoreInfo(false);
                                }
                            }).create();
        }
    }
}

