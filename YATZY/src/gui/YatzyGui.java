package gui;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Yatzy;


import java.util.Random;


public class YatzyGui extends Application {


    @Override
    public void start(Stage stage) {
        stage.setTitle("Yatzy");
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // -------------------------------------------------------------------------

    private Yatzy dice = new Yatzy();
    // Shows the face values of the 5 dice.
    private TextField[] txfValues = new TextField[5];
    // Shows the hold status of the 5 dice.
    private CheckBox[] chbHolds = new CheckBox[5];
    // Shows the results previously selected .
    // For free results (results not set yet), the results
    // corresponding to the actual face values of the 5 dice are shown.
    private TextField[] txfResults = new TextField[15];

    private Label[] lblResults = new Label[15];

    // Shows points in sums, bonus and total.
    private TextField txfSumSame, txfBonus, txfSumOther, txfTotal;
    // Shows the number of times the dice has been rolled.
    private Label lblRolled = new Label("Antal rul:  ");

    private Button btnRoll = new Button("Rul");

    Random random = new Random();
    int total = 0;
    int sumSame = 0;
    int sumOther = 0;
    int bonusPoints = 0;
    boolean endGame = false;

    int w = 50; // width of the text fields

    private void initContent(GridPane pane) {
        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);

        // ---------------------------------------------------------------------

        GridPane dicePane = new GridPane();
        pane.add(dicePane, 0, 0, 10, 10);
        dicePane.setGridLinesVisible(false);
        dicePane.setPadding(new Insets(10));
        dicePane.setHgap(10);
        dicePane.setVgap(10);
        dicePane.setStyle("-fx-border-color: black");


        // initialize txfValues, chbHolds, btnRoll and lblRolled
        // TODO

        dicePane.add(btnRoll, 3, 3);
        btnRoll.setOnAction(event -> btnRollAction());

        dicePane.add(lblRolled, 4, 3);


        for (int i = 0; i < txfValues.length; i++) {
            txfValues[i] = new TextField();
        }
        for (int i = 0; i < txfValues.length; i++) {
            dicePane.add(txfValues[i], i, 1);
            txfValues[i].setEditable(false);
            txfValues[i].setPrefHeight(w);
            txfValues[i].setPrefWidth(w);
        }

        for (int i = 0; i < chbHolds.length; i++) {
            chbHolds[i] = new CheckBox("Hold");
            chbHolds[i].setSelected(false);
        }
        for (int i = 0; i < chbHolds.length; i++) {
            dicePane.add(chbHolds[i], i, 2);
        }
        // ---------------------------------------------------------------------

        GridPane scorePane = new GridPane();
        pane.add(scorePane, 0, 11, 10, 10);
        scorePane.setGridLinesVisible(false);
        scorePane.setPadding(new Insets(10));
        scorePane.setVgap(5);
        scorePane.setHgap(10);
        scorePane.setStyle("-fx-border-color: black");


        // Initialize labels for results, txfResults,
        // labels and text fields for sums, bonus and total.
        // TODO
        /**
         * TEXTFIELDS MED RESULTATER
         */
        for (int i = 0; i < txfResults.length; i++) {
            txfResults[i] = new TextField();
            txfResults[i].setEditable(false);
        }
        for (int i = 0; i < txfResults.length; i++) {
            scorePane.add(txfResults[i], 2, i);
            txfResults[i].setPrefWidth(w);
            txfResults[i].setOnMouseClicked(event -> this.selectTxfResult(event));
        }
        /**
         * BONUS
         */
        txfBonus = new TextField();
        txfBonus.setPrefWidth(w);
        scorePane.add(txfBonus, 6, 5);
        Label lblBonus = new Label("Bonus");
        scorePane.add(lblBonus, 5, 5);
        txfBonus.setEditable(false);


        /**
         * SUM SAME
         */
        txfSumSame = new TextField();
        txfSumSame.setPrefWidth(w);
        scorePane.add(txfSumSame, 4, 5);
        Label lblSumSame = new Label("Sum");
        scorePane.add(lblSumSame, 3, 5);
        txfSumSame.setEditable(false);


        /**
         * TOTAL SCORE
         */
        txfTotal = new TextField();
        txfTotal.setPrefWidth(w);
        scorePane.add(txfTotal, 6, 14);
        Label lblTotalScore = new Label("Total");
        scorePane.add(lblTotalScore, 5, 14);
        txfTotal.setEditable(false);


        /**
         * SUM MED I ALT
         */
        txfSumOther = new TextField();
        txfSumOther.setPrefWidth(w);
        scorePane.add(txfSumOther, 4, 14);
        Label lblSumOther = new Label("Sum");
        scorePane.add(lblSumOther, 3, 14);
        txfSumOther.setEditable(false);

        /**
         * Labels med resultater
         */
        for (int i = 0; i < lblResults.length; i++) {
            String[] result = getLblResults();
            lblResults[i] = new Label(result[i]);
            scorePane.add(lblResults[i], 0, i);
        }

        for (int i = 0; i < txfResults.length; i++) {
            int[] result = dice.getResults();
            txfResults[i].equals(result[i]);

        }
        btnRoll.setOnAction(event -> btnRollAction());

    }

    // -------------------------------------------------------------------------

    private String[] getLblResults() {
        String[] pointTyper = new String[15];
        pointTyper[0] = "1'ere";
        pointTyper[1] = "2'ere";
        pointTyper[2] = "3'ere";
        pointTyper[3] = "4'ere";
        pointTyper[4] = "5'ere";
        pointTyper[5] = "6'ere";
        pointTyper[6] = "Et par";
        pointTyper[7] = "To par";
        pointTyper[8] = "Tre ens";
        pointTyper[9] = "Fire ens";
        pointTyper[10] = "Fuld hus";
        pointTyper[11] = "Lille trappe";
        pointTyper[12] = "Store trappe";
        pointTyper[13] = "Chancen";
        pointTyper[14] = "Yatzy";

        return pointTyper;
    }

    // Create a method for btnRoll's action.
    // Hint: Create small helper methods to be used in the action method.
    // TODO
    private void btnRollAction() {
        disableBtnRoll(); // disabler btnRoll når throwCount = 3
        dice.throwDice(holds());
        updateTextFieldWithFaceValue();
        lblRolled.setText("Antal rul: " + dice.getThrowCount());
        for(CheckBox chbHold : chbHolds){
            chbHold.setDisable(false);
        }
        keepChosenValues();
    }

    private void disableBtnRoll() {
        if (dice.getThrowCount() == 2) {
            btnRoll.setDisable(true);
        }
    }

    private boolean[] holds() {
        boolean[] holds = new boolean[chbHolds.length];
        for (int i = 0; i < holds.length; i++) {
            if (chbHolds[i].isSelected()) {
                holds[i] = true;
            }
        }
        return holds;
    }


    /**
     * opdatere textfelterne med de fem terning slag
     */
    private void updateTextFieldWithFaceValue() {
        for (int i = 0; i < txfValues.length; i++) {
            if (!chbHolds[i].isSelected()) {
                txfValues[i].setText(String.valueOf(dice.getValues()[i]));
            }
        }
    }
    // -------------------------------------------------------------------------

    // Create a method for mouse click on one of the text fields in txfResults.
    // Hint: Create small helper methods to be used in the mouse click method.
    // TODO

    /**
     *
     * @param event
     */
    private void selectTxfResult(MouseEvent event) {
        TextField textField = (TextField) event.getSource();
        textField.setDisable(true);
        this.sumSame = 0;
        this.sumOther = 0;
        this.bonusPoints = 0;
        this.total = 0;

        for (int i = 0; i < txfResults.length; i++) {
            if (txfResults[i].isDisabled() && i <= txfValues.length) {
                sumSame += Integer.parseInt(txfResults[i].getText());
                total += Integer.parseInt(txfResults[i].getText());
            } else if (txfResults[i].isDisabled() && i > txfValues.length) {
                sumOther += Integer.parseInt(txfResults[i].getText());
                total += Integer.parseInt(txfResults[i].getText());
            }
        }if(sumSame >= 63){
            bonusPoints = 50;
            total += bonusPoints;
        }
        txfSumSame.setText(String.valueOf(sumSame));
        txfSumOther.setText(String.valueOf(sumOther));
        txfTotal.setText(String.valueOf(total));
        txfBonus.setText(String.valueOf(bonusPoints));
        updateField();
        checkTxfResults();
        if(checkTxfResults()==true){
            afslutSpilAction();
        }
    }

    /**
     * updates fields and checkboxes
     */

    private void updateField(){
        for (CheckBox chbHold : chbHolds) {
            if(chbHold.isSelected()){
                chbHold.setSelected(false);
            }
        }
        for(TextField txfResult : txfResults){
            if(!txfResult.isDisabled()){
                txfResult.setText("0");
            }
        }
        dice.resetThrowCount();
        btnRoll.setDisable(false);
        lblRolled.setText("Rolled: " + dice.getThrowCount());

        for(TextField txfValue : txfValues){
            if(!txfValue.isDisabled()){
                txfValue.setText(null);
            }
        }
    }

    /**
     * locks the selected textfield values
     */
    private void keepChosenValues(){
        for (int i = 0; i < dice.getResults().length; i++) {
            if(txfResults[i].isDisabled()){
                // Der skal ikke ske noget
            } else {
                txfResults[i].setText(String.valueOf(dice.getResults()[i]));
            }
        }
    }

    /**
     * popup window with final score
     */
    public void afslutSpilAction() {
        Alert manglerinfo = new Alert(Alert.AlertType.INFORMATION);
        manglerinfo.setTitle("Spil er slut!");
        manglerinfo.setHeaderText("Tak for spillet!");
        manglerinfo.setContentText("Din score blev: " + total);
        manglerinfo.showAndWait();
        if(manglerinfo!=null){
            resetGame();
        }


    }

    /**
     * checks is all txfResults textfields is selected
     * @return
     */
    private boolean checkTxfResults(){
        this.endGame = false;
        for (int i = 0; i < txfResults.length; i++) {
            if(!txfResults[i].isDisabled()){
                this.endGame = false;
                break;
            }else {
                this.endGame = true;
            }
        }
        return this.endGame;
    }

    /**
     * resets game
     */
    private void resetGame(){
        this.sumSame = 0;
        this.sumOther = 0;
        this.bonusPoints = 0;
        this.total = 0;
        this.endGame = false;
        updateField();
        for (int i = 0; i < txfResults.length; i++) {
            txfResults[i].clear();
            txfResults[i].setDisable(false);
        }
    }

}
