package com.muc;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.awt.event.MouseEvent;

/**
 * creates a JPanel that shows users that are logged in
 * and others that are logged off
 */
public class UserListPane extends JPanel implements UserStatusListener{
    private final ChatClient client;
    private JList<String> userListUI;
    private DefaultListModel<String> userListModel;
    public UserListPane(ChatClient client){
        this.client=client;
        this.client.addUserStatusListener(this);
        userListModel = new DefaultListModel<>();
        userListUI = new JList<>();
        setLayout(new BorderLayout());
        add(new JScrollPane(userListUI),BorderLayout.CENTER);
        userListUI.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()>1){
                    String login =userListUI.getSelectedValue();
                    MessagePane messagePane = new MessagePane(client,login);
                    JFrame f = new JFrame("Message: "+ login);
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setSize(500,500);
                    f.getContentPane().add(messagePane, BorderLayout.CENTER);
                    f.setVisible(true);
                }
            }
        });
    }

    /**
     * runs the JPanel
     * @param args
     */
    public static void main(String[] args){
        ChatClient client = new ChatClient("localhost",8818);
        UserListPane userListPane = new UserListPane(client);
        JFrame frame = new JFrame("User List");
        frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        frame.setSize(400,600);
        frame.getContentPane().add(new JScrollPane(userListPane), BorderLayout.CENTER);
        frame.setVisible(true);

        if(client.connect()){
            try {
                client.login("guest", "guest");
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onLine(String login){
        userListModel.addElement(login);
    }
    @Override
    public void offline(String login){
        userListModel.removeElement(login);
    }
}
