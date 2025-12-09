import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class LoginFrame extends JFrame {
        private JTextField tfUsername;
        private JPasswordField pfPassword;
        private JButton btnLogin;
        private JButton btnCancel;
        private JCheckBox cbShowPassword;
        private Map<String, String> credentials = new HashMap<>();

        public LoginFrame() {
                super("Login");
                initCredentials();
                initComponents();
                layoutComponents();
                attachListeners();
        
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                pack();
                setLocationRelativeTo(null);
                setResizable(false);
        }
    
        private void initCredentials() {
                credentials.put("admin", "password");
                credentials.put("user", "user123");
        }
    
        private void initComponents() {
                tfUsername = new JTextField(18);
                pfPassword = new JPasswordField(18);
                btnCancel = new JButton("Cancel");
                btnLogin = new JButton("Login");
                cbShowPassword = new JCheckBox("Show password");
        }
    
        private void layoutComponents() {
                JPanel content = new JPanel(new BorderLayout(10, 10));
                content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
                JPanel fields = new JPanel(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                c.insets = new Insets(6, 6, 6, 6);
                c.anchor = GridBagConstraints.WEST;
        
                c.gridx = 0; c.gridy = 0;
                fields.add(new JLabel("Username:"), c);
                c.gridx = 1;
                fields.add(tfUsername, c);
        
                c.gridx = 0; c.gridy = 1;
                fields.add(new JLabel("Password:"), c);
                c.gridx = 1;
                fields.add(pfPassword, c);
        
                c.gridx = 1; c.gridy = 2;
                fields.add(cbShowPassword, c);
        
                JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttons.add(btnLogin);
                buttons.add(btnCancel);
                
        
                content.add(fields, BorderLayout.CENTER);
                content.add(buttons, BorderLayout.SOUTH);
        
                setContentPane(content);
        }
    
        private void attachListeners() {
                cbShowPassword.addActionListener(e -> {
                    pfPassword.setEchoChar(cbShowPassword.isSelected() ? (char)0 : '\u2022');
                });
        
                btnCancel.addActionListener(e -> {
                    tfUsername.setText("");
                    pfPassword.setText("");
                    tfUsername.requestFocus();
                });
        
                btnLogin.addActionListener(e -> attemptLogin());
        
                Action enterAction = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        attemptLogin();
                    }
                };
                tfUsername.addActionListener(enterAction);
                pfPassword.addActionListener(enterAction);
        }
    
        private void attemptLogin() {
                String user = tfUsername.getText().trim();
                String pass = new String(pfPassword.getPassword());
        
                if (user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Username atau password tidak boleh kosong.",
                            "Login error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
        
                if (authenticate(user, pass)) {
                    JOptionPane.showMessageDialog(this,
                            "Login berhasil. Selamat datang, " + user + "!",
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE);
                    openMainWindow(user);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Username atau password salah.",
                            "Login gagal",
                            JOptionPane.ERROR_MESSAGE);
                    pfPassword.setText("");
                    pfPassword.requestFocus();
                }
        }
    
        private boolean authenticate(String username, String password) {
                if (!credentials.containsKey(username)) return false;
                String expected = credentials.get(username);
                return expected.equals(password);
        }
    
        private void openMainWindow(String username) {
                JFrame main = new JFrame("Main - Welcome " + username);
                main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                main.setSize(400, 200);
                main.setLocationRelativeTo(this);
                JLabel lbl = new JLabel("Anda telah masuk sebagai: " + username, SwingConstants.CENTER);
                main.add(lbl);
                main.setVisible(true);
        }
    
        public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> {
                    new LoginFrame().setVisible(true);
                });
        }
}