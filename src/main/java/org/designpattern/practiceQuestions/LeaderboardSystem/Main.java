package org.designpattern.practiceQuestions.LeaderboardSystem;

import org.designpattern.practiceQuestions.LeaderboardSystem.Factory.GameType;
import org.designpattern.practiceQuestions.LeaderboardSystem.ObservorPattern.InAppNotification;
import org.designpattern.practiceQuestions.LeaderboardSystem.ObservorPattern.SMS;
import org.designpattern.practiceQuestions.LeaderboardSystem.Strategy.SimpleSort;
import org.designpattern.practiceQuestions.LeaderboardSystem.UtilityClass.Player;

public class Main {
    public static void main(String[] args) {

        Player player1 = new Player("Priya P1");
        Player player2 = new Player("Sexy Haotami P2");
        Player player3 = new Player("yogitaro P3");
        Player player4 = new Player("akutaro P4");
        SMS sms1 = new SMS("7905398325");
        InAppNotification inAppNotification1= new InAppNotification("player1");
        player1.addObservor(sms1);
        player1.addObservor(inAppNotification1);

        SMS sms2 = new SMS("7905398325");
        InAppNotification inAppNotification2= new InAppNotification("player2");
        player2.addObservor(sms2);
        player2.addObservor(inAppNotification2);

        SMS sms3 = new SMS("8905398325");
        InAppNotification inAppNotification3= new InAppNotification("player3");
        player3.addObservor(sms3);
        player3.addObservor(inAppNotification3);

        SMS sms4 = new SMS("9905398325");
        InAppNotification inAppNotification4= new InAppNotification("player4");
        player4.addObservor(sms4);
        player4.addObservor(inAppNotification4);

        Game game = GameType.getGameType("Solo");
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        player1.setScore(100);
        player2.setScore(200);
        player3.setScore((600));
        game.calculateLeaderBoard(game.players,2);

        System.out.println("********** Game 2********************");
        System.out.println();

        Game game2= GameType.getGameType("Team");
        game2.addPlayer(player2);
        game2.addPlayer(player3);
        game2.addPlayer(player4);
        player2.setScore(500);
        player3.setScore(200);
        player4.setScore(800);
        player4.setScore(100);
        game2.calculateLeaderBoard(game2.players,2);
        System.out.println("Player 3 rank "+ player3.getRank());

        System.out.println("********** Game 2 with SimpleSort Strategy ********************");
        System.out.println();
        game2.setLeaderBoardStrategy(new SimpleSort());
        game2.calculateLeaderBoard(game2.players,2);

    }
}
