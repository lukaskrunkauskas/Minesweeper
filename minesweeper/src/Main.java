public class Main  implements Runnable{
    UserInterface userInterface = new UserInterface();

    public static void main(String[] args) {
        new Thread(new Main()).start();
    }

    @Override
    public void run() {
        while(true) {
            userInterface.repaint();
            if(userInterface.resetter == false) {
                userInterface.checkIfWon();
            }

        }
    }

}
