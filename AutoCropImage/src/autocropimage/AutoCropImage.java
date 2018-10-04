package autocropimage;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutoCropImage extends javax.swing.JFrame {

    private SystemTray systemTray;
    private TrayIcon trayIcon;
    private Thread t;
    private boolean status;

    public AutoCropImage() {
        setType(javax.swing.JFrame.Type.UTILITY);
        setAlwaysOnTop(true);
        initComponents();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - getWidth();
        int y = (int) rect.getMaxY() - getHeight() - 43;
        setLocation(x, y);
        initSystemTray();
        status = true;
        initThread();
        t.start();
    }

    private void initThread() {
        t = new Thread() {
            @Override
            public void run() {
                try {
                    Path path = Paths.get("D:/Anhsieuam");
                    WatchService watchService = path.getFileSystem().newWatchService();
                    path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
                    while (true) {
                        WatchKey watchKey = watchService.take();
                        watchKey.pollEvents().forEach((event) -> {
                            String content = event.context().toString();
                            String ex = content.substring(content.length() - 3);
                            if (ex.equals("bmp")) {
                                sendMsg(content);
                            }
                        });
                        watchKey.reset();
                    }
                } catch (InterruptedException | IOException ex) {
                    Logger.getLogger(AutoCropImage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }
    
    private void sendMsg(String img) {
        String command = "java -jar C:\\AutoCropImage\\ProcessCropImage.jar " + img + " " + status;
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
        try {
            Process p = builder.start();
        } catch (IOException ex) {
            Logger.getLogger(AutoCropImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initSystemTray() {
        if (!SystemTray.isSupported()) {
            systemTray = null;
            return;
        }
        systemTray = SystemTray.getSystemTray();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(getClass().getResource("favicon.gif"));
        trayIcon = new TrayIcon(image, "Tự động cắt ảnh");
        trayIcon.setImageAutoSize(true);

        PopupMenu popup = new PopupMenu();
        MenuItem item1 = new MenuItem("Ảnh thường");
        MenuItem item2 = new MenuItem("Ảnh cũ");
        MenuItem exitItem = new MenuItem("Thoát");

        popup.add(item1);
        popup.add(item2);
        popup.addSeparator();
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        item1.addActionListener((ActionEvent e) -> {
            changeStatus(true);
        });

        item2.addActionListener((ActionEvent e) -> {
            changeStatus(false);
        });

        exitItem.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        try {
            systemTray.add(trayIcon);
        } catch (AWTException ex) {
            Logger.getLogger(AutoCropImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void changeStatus(boolean status) {
        this.status = status;
        if(status) {
            jButton1.setText("Ảnh thường");
        } else {
            jButton1.setText("Ảnh cũ");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tự động cắt ảnh");
        setUndecorated(true);
        setOpacity(0.8F);
        setResizable(false);

        jButton1.setText("Ảnh thường");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        changeStatus(!status);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Windows look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AutoCropImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AutoCropImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AutoCropImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AutoCropImage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AutoCropImage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
