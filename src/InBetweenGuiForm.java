import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.channels.ScatteringByteChannel;
import java.util.Random;

public class InBetweenGuiForm {
    public static Random rand = new Random();
    private JPanel panel;
    private JLabel lblOne, lblTwo, lblThree;
    private JTextField txtRounds;
    private JButton btnReveal, btnBet, btnFold, btnLow, btnHigh;
    private JButton btnReset;
    private JTextField txtPocketMoney, txtBetMoney;
    private JLabel lblPocketMoney, lblPlaceBet;
    private JLabel lblTitle;
    private JButton btnQuit;
    private JButton btnHowToPlay;
    int max;
    int min;
    int range;
    int cardOne;
    int cardTwo;
    int cardThree;
    double betMoney;
    public double pocketMoney = 1000;
    public int rounds = 0;

    public InBetweenGuiForm() {
        defaultCard();
        if(pocketMoney <= 0) {
            JOptionPane.showMessageDialog(null, "Sorry, insufficient money.");
            System.exit(0);
        }
        btnFold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defaultCard();
            }
        });
        btnReveal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                                                                                                                                  
                lblPlaceBet.setVisible(true);
                txtBetMoney.setVisible(true);                                 
                txtBetMoney.setEnabled(true);
                btnReveal.setVisible(false);
                btnBet.setVisible(true);
                btnQuit.setVisible(false);
                btnFold.setVisible(true);
                btnBet.setEnabled(true);
                btnFold.setEnabled(true);
                max = 13;
                min = 1;
                range = max - min + 1;
                cardOne = (int) (Math.random() * range) + min;
                cardTwo = (int) (Math.random() * range) + min;                                                         
                cardThree = (int) (Math.random() * range) + min;
                showCardOne(cardOne);
                showCardTwo(cardTwo);
                if ((cardOne == 1 && cardTwo == 1) || (cardOne == 13 && cardTwo == 13)
                        || (cardOne == cardTwo + 1) || (cardOne == cardTwo - 1) || (cardTwo == cardOne + 1)
                        || (cardTwo == cardOne - 1))
                {
                    JOptionPane.showMessageDialog(null, "Please re-draw your cards!");
                    rounds--;
                    btnHigh.setVisible(false);
                    btnLow.setVisible(false);
                    btnBet.setVisible(false);
                    btnFold.setVisible(false);
                    btnQuit.setVisible(false);
                    lblPlaceBet.setVisible(false);
                    txtBetMoney.setVisible(false);
                    defaultCard();
                }
                else {

                    if (cardOne != cardTwo) {
                        btnHigh.setVisible(false);
                        btnLow.setVisible(false);
                        btnBet.setVisible(true);
                        btnFold.setVisible(true);
                    }
                    else {
                        btnHigh.setVisible(true);
                        btnLow.setVisible(true);
                        btnBet.setVisible(false);
                        btnFold.setVisible(false);
                    }
                }

            }
        });
        btnBet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    betMoney = Double.parseDouble(txtBetMoney.getText());
                    if (betMoney > pocketMoney) {
                        JOptionPane.showMessageDialog(null, "Sorry, insufficient money.");
                        txtBetMoney.setText("");
                    } else {
                        showCardThree(cardThree);
                        if ((cardThree > cardOne && cardThree < cardTwo) || (cardThree < cardOne && cardThree > cardTwo)) {
                            JOptionPane.showMessageDialog(null, "Congrats! You win P" + String.format("%,.2f", betMoney) + " in in-between!");
                            pocketMoney += betMoney;
                        } else {
                            JOptionPane.showMessageDialog(null, "Sorry! You lose P" + String.format("%,.2f", betMoney) + " in in-between!");
                            pocketMoney -= betMoney;
                        }
                        defaultCard();
                    }

                }
                catch(RuntimeException ex)
                {
                    JOptionPane.showMessageDialog(null, "You have entered invalid input!");
                    txtBetMoney.setText("");
                }
            }

        });
        btnHigh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLow.setVisible(false);
                btnReveal.setVisible(false);
                try
                {
                betMoney= Double.parseDouble(txtBetMoney.getText());
                if (betMoney > pocketMoney) {
                    JOptionPane.showMessageDialog(null, "Sorry, insufficient money.");
                    txtBetMoney.setText("");
                    btnLow.setVisible(true);
                }
                else {
                    showCardThree(cardThree);
                    if (cardThree > cardOne) {
                        JOptionPane.showMessageDialog(null, "The third card is higher! You win P" + String.format("%,.2f", betMoney) + " in High or Low!");
                        pocketMoney += betMoney;
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "The third card is lower!! You lose P" + String.format("%,.2f", betMoney) + " in High or Low!");
                        pocketMoney -= betMoney;
                    }
                    defaultCard();
                }

                }
                catch(RuntimeException ex)
                {
                    JOptionPane.showMessageDialog(null, "You have entered invalid input!");
                    txtBetMoney.setText("");
                    btnLow.setVisible(true);
                }
            }
        });
        btnLow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnHigh.setVisible(false);
                btnReveal.setVisible(false);
                try {
                    betMoney = Double.parseDouble(txtBetMoney.getText());
                    if (betMoney > pocketMoney) {
                        JOptionPane.showMessageDialog(null, "Sorry, insufficient money.");
                        txtBetMoney.setText("");
                        btnHigh.setVisible(true);
                    } else {
                        showCardThree(cardThree);
                        if (cardThree < cardOne) {
                            JOptionPane.showMessageDialog(null, "The third card is lower! You win P" + String.format("%,.2f", betMoney) + " in High or Low!");
                            pocketMoney += betMoney;
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "The third card is higher! You lose P" + String.format("%,.2f", betMoney) + " in High or Low!");
                            pocketMoney -= betMoney;
                        }
                        defaultCard();
                    }
                }
                catch(RuntimeException ex)
                {
                    JOptionPane.showMessageDialog(null, "You have entered invalid input!");
                    txtBetMoney.setText("");
                    btnHigh.setVisible(true);
                }
            }
        });
        btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Thank you for playing! " +
                        "\nThe total money you can take home is P" + String.format("%,.2f", pocketMoney) + ".");
                txtBetMoney.setText("");
                System.exit(0);
            }
        });
        btnHowToPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "HOW TO PLAY" +
                        "\n " +
                        "\nAt the very start of the game, the user have a pocket money of P1,000." +
                        "\nTo play the game, the user must click the \"SHOW CARDS\" to start the round," +
                        "\nthen two cards will face up, and now the user has an option to BET or FOLD." +
                        "\nIf the user choose \"BET\", the users needs to enter the amount of bet he/she wants" +
                        "\nto bet then a third card will be drawn." +
                        "\nIf the third card is in-between of the first two cards, you win, if not, you lose!" +
                        "\nIf the user choose \"FOLD\", a round will be consumed. The user have 5 rounds only to play. " +
                        "\n " +
                        "\nNow if the first two drawn cards is same number, the game will be \"HIGH OR LOW\"." +
                        "\nAlso here, the users needs to enter the amount of bet he/she wants to bet" +
                        "\nthen a third card will be drawn. " +
                        "\nThe user must guess if the third card will be HIGHER OR LOWER." +
                        "\nIf the guess is correct, the user wins and if it's wrong, the user will lose." +
                        "\n " +
                        "\n*Remember: The user have 5 rounds only, so seize the game and bet it all!." +
                        "\nDon't worry, you can quit any time unless you didn't press the \"SHOW CARDS\" yet." +
                        "\nEnjoy playing!");
            }
        });
    }

    public void defaultCard()
    {
        lblPlaceBet.setVisible(false);
        txtBetMoney.setVisible(false);
        btnBet.setVisible(false);
        btnFold.setVisible(false);
        btnQuit.setVisible(true);
        btnReveal.setVisible(true);
        btnReveal.setEnabled(true);
        btnHigh.setVisible(false);
        btnLow.setVisible(false);
        btnBet.setEnabled(false);
        btnFold.setEnabled(false);
        ImageIcon playCardOne = new ImageIcon(new ImageIcon("back_card.jpg").getImage()
                .getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        lblOne.setIcon(playCardOne);
        ImageIcon playCardTwo = new ImageIcon(new ImageIcon("back_card.jpg").getImage()
                .getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        lblTwo.setIcon(playCardTwo);
        ImageIcon playCardThree = new ImageIcon(new ImageIcon("back_card.jpg").getImage()
                .getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        lblThree.setIcon(playCardThree);
        rounds++;
        txtBetMoney.setText("");
        txtPocketMoney.setText(String.format("%,.2f", pocketMoney));
        if(pocketMoney <= 0) {
            JOptionPane.showMessageDialog(null, "Sorry, insufficient money.");
            System.exit(0);
        }
        if (rounds == 6)
        {
            JOptionPane.showMessageDialog(null, "You've played your all rounds! Thanks for playing! " +
                    "\nThe total money you can take home is P" + String.format("%,.2f", pocketMoney) + "." + "\nCome again!");
            txtPocketMoney.setText(String.format("%,.2f", pocketMoney));
            txtBetMoney.setText("");
            System.exit(0);
        }
        else if(rounds <= 5)
        {
            txtRounds.setText(String.valueOf(rounds));
        }

    }
    public void showCardOne(int cardOne) {
        if (cardOne == 1) {
            ImageIcon playCardOne = new ImageIcon(new ImageIcon("AH.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblOne.setIcon(playCardOne);
        } else if (cardOne == 2) {
            ImageIcon playCardOne = new ImageIcon(new ImageIcon("2H.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblOne.setIcon(playCardOne);
        } else if (cardOne == 3) {
            ImageIcon playCardOne = new ImageIcon(new ImageIcon("3H.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblOne.setIcon(playCardOne);
        } else if (cardOne == 4) {
            ImageIcon playCardOne = new ImageIcon(new ImageIcon("4H.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblOne.setIcon(playCardOne);
        } else if (cardOne == 5) {
            ImageIcon playCardOne = new ImageIcon(new ImageIcon("5H.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblOne.setIcon(playCardOne);
        } else if (cardOne == 6) {
            ImageIcon playCardOne = new ImageIcon(new ImageIcon("6H.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblOne.setIcon(playCardOne);
        } else if (cardOne == 7) {
            ImageIcon playCardOne = new ImageIcon(new ImageIcon("7H.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblOne.setIcon(playCardOne);
        } else if (cardOne == 8) {
            ImageIcon playCardOne = new ImageIcon(new ImageIcon("8H.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblOne.setIcon(playCardOne);
        } else if (cardOne == 9) {
            ImageIcon playCardOne = new ImageIcon(new ImageIcon("9H.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblOne.setIcon(playCardOne);
        } else if (cardOne == 10) {
            ImageIcon playCardOne = new ImageIcon(new ImageIcon("10H.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblOne.setIcon(playCardOne);
        } else if (cardOne == 11) {
            ImageIcon playCardOne = new ImageIcon(new ImageIcon("JH.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblOne.setIcon(playCardOne);
        } else if (cardOne == 12) {
            ImageIcon playCardOne = new ImageIcon(new ImageIcon("QH.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblOne.setIcon(playCardOne);
        } else if (cardOne == 13) {
            ImageIcon playCardOne = new ImageIcon(new ImageIcon("KH.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblOne.setIcon(playCardOne);
        }
    }
    public void showCardTwo(int cardTwo)
    {
        if (cardTwo == 1) {
            ImageIcon playCardTwo = new ImageIcon(new ImageIcon("AS.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblTwo.setIcon(playCardTwo);
        } else if (cardTwo == 2) {
            ImageIcon playCardTwo = new ImageIcon(new ImageIcon("2S.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblTwo.setIcon(playCardTwo);
        } else if (cardTwo == 3) {
            ImageIcon playCardTwo = new ImageIcon(new ImageIcon("3S.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblTwo.setIcon(playCardTwo);
        } else if (cardTwo == 4) {
            ImageIcon playCardTwo = new ImageIcon(new ImageIcon("4S.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblTwo.setIcon(playCardTwo);
        } else if (cardTwo == 5) {
            ImageIcon playCardTwo = new ImageIcon(new ImageIcon("5S.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblTwo.setIcon(playCardTwo);
        } else if (cardTwo == 6) {
            ImageIcon playCardTwo = new ImageIcon(new ImageIcon("6S.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblTwo.setIcon(playCardTwo);
        } else if (cardTwo == 7) {
            ImageIcon playCardTwo = new ImageIcon(new ImageIcon("7S.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblTwo.setIcon(playCardTwo);
        } else if (cardTwo == 8) {
            ImageIcon playCardTwo = new ImageIcon(new ImageIcon("8S.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblTwo.setIcon(playCardTwo);
        } else if (cardTwo == 9) {
            ImageIcon playCardTwo = new ImageIcon(new ImageIcon("9S.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblTwo.setIcon(playCardTwo);
        } else if (cardTwo == 10) {
            ImageIcon playCardTwo = new ImageIcon(new ImageIcon("10S.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblTwo.setIcon(playCardTwo);
        } else if (cardTwo == 11) {
            ImageIcon playCardTwo = new ImageIcon(new ImageIcon("JS.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblTwo.setIcon(playCardTwo);
        } else if (cardTwo == 12) {
            ImageIcon playCardTwo = new ImageIcon(new ImageIcon("QS.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblTwo.setIcon(playCardTwo);
        } else if (cardTwo == 13) {
            ImageIcon playCardTwo = new ImageIcon(new ImageIcon("KS.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblTwo.setIcon(playCardTwo);
        }
    }
    public void showCardThree(int cardThree)
    {
        if (cardThree == 1) {
            ImageIcon playCardThree = new ImageIcon(new ImageIcon("AC.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblThree.setIcon(playCardThree);
        }
        else if (cardThree == 2) {
            ImageIcon playCardThree = new ImageIcon(new ImageIcon("2C.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblThree.setIcon(playCardThree);
        }
        else if (cardThree == 3) {
            ImageIcon playCardThree = new ImageIcon(new ImageIcon("3C.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblThree.setIcon(playCardThree);
        }
        else if (cardThree == 4) {
            ImageIcon playCardThree = new ImageIcon(new ImageIcon("4C.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblThree.setIcon(playCardThree);
        }
        else if (cardThree == 5) {
            ImageIcon playCardThree = new ImageIcon(new ImageIcon("5C.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblThree.setIcon(playCardThree);
        }
        else if (cardThree == 6) {
            ImageIcon playCardThree = new ImageIcon(new ImageIcon("6C.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblThree.setIcon(playCardThree);
        }
        else if (cardThree == 7) {
            ImageIcon playCardThree = new ImageIcon(new ImageIcon("7C.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblThree.setIcon(playCardThree);
        }
        else if (cardThree == 8) {
            ImageIcon playCardThree = new ImageIcon(new ImageIcon("8C.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblThree.setIcon(playCardThree);
        }
        else if (cardThree == 9) {
            ImageIcon playCardThree = new ImageIcon(new ImageIcon("9C.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblThree.setIcon(playCardThree);
        }
        else if (cardThree == 10) {
            ImageIcon playCardThree = new ImageIcon(new ImageIcon("10C.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblThree.setIcon(playCardThree);
        }
        else if (cardThree == 11) {
            ImageIcon playCardThree = new ImageIcon(new ImageIcon("JC.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblThree.setIcon(playCardThree);
        }
        else if (cardThree == 12) {
            ImageIcon playCardThree = new ImageIcon(new ImageIcon("QC.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblThree.setIcon(playCardThree);
        }
        else if (cardThree == 13) {
            ImageIcon playCardThree = new ImageIcon(new ImageIcon("KC.png")
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            lblThree.setIcon(playCardThree);
        }
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("In-Between Game");
        frame.setPreferredSize(new Dimension(900, 550));
        frame.setContentPane(new InBetweenGuiForm().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(250, 125);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setTitle("IN-BETWEEN GAME - Neil Rendell Reglos");
        frame.setBackground(Color.cyan);
        //JOptionPane opMessage = new JOptionPane("In-Between Game");
        //opMessage.setLocation(400, 200);
    }
}

