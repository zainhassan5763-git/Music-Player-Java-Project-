import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
public class GUIMXPLAYER extends javax.swing.JFrame {
    // ===== Music player state variables =====
    private boolean isPlaying = false; // Track if music is currently playing
    private Clip clip;                 // Clip to play .wav music
    private Timer progressTimer;
    private File currentFile = null; // Tracks currently playing file
    private ImageIcon playIcon = new ImageIcon(getClass().getResource("/play.png"));
    private ImageIcon pauseIcon = new ImageIcon(getClass().getResource("/pause.png"));

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GUIMXPLAYER.class.getName());

    /**
     * Creates new form GUIMXPLAYER
     */

    public GUIMXPLAYER() {
        initComponents();
        
        // Set JFrame icon
        setIconImage(
            new ImageIcon(getClass().getResource("/icons/titleicon.png")).getImage()
        );
        loadSongs("D:\\Download\\Java Project\\Music player\\wav songs");
        // Timer to update progress bar every 500ms
        progressTimer = new Timer(1000, e -> updateProgressBar());
        progressTimer.start();
    }

private void loadSongs(String folderPath) {
    File folder = new File(folderPath);
    if (!folder.exists()) {
        JOptionPane.showMessageDialog(this, "Folder not found!");
        return;
    }


    File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));
    
    if (files == null || files.length == 0) {
        JOptionPane.showMessageDialog(this, "No .wav files found in folder!");
        return;
    }


    String[] songNames = new String[files.length];
    for (int i = 0; i < files.length; i++) {
        songNames[i] = files[i].getName(); // Display file name only
    }


    songList.setListData(songNames);
}

private void playSongAtIndex(int index) {
    if (index < 0 || index >= songList.getModel().getSize()) return;

    songList.setSelectedIndex(index);
    String songName = songList.getModel().getElementAt(index);

    String folderPath = "D:\\Download\\Java Project\\Music player\\wav songs";
    File file = new File(folderPath, songName);

    if (!file.exists()) return;

    if (clip != null) {
        clip.stop();
        clip.close();
    }

    currentFile = file;
    playMusic();

    playPauseButton.setIcon(pauseIcon);
    isPlaying = true;
    jProgressBar1.setValue(0);
}

private void updateProgressBar() {
    if (clip != null && clip.isOpen()) {
        long current = clip.getMicrosecondPosition();   // current position in microseconds
        long total = clip.getMicrosecondLength();       // total length in microseconds

        if (total > 0) {
            int progress = (int) ((current * 100) / total); // percentage
            jProgressBar1.setValue(progress);
        } else {
            jProgressBar1.setValue(0);
        }
    } else {
        jProgressBar1.setValue(0);
    }
}

    // ===== Play selected music =====
    private void playMusic(){
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
        try {
            if (currentFile == null) return; // nothing selected
            File file = currentFile;    

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
            setVolume(volumeButton.getValue());
        }catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error playing this file!");
        }
    }
    // ===== Pause / Resume music =====
    private void pauseMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop(); // Pause
            clip.close();
        } else if (clip != null) {
            clip.start(); // Resume
    }
}

// ===== Toggle Play/Pause for the button =====
    private void togglePlayPause() {
         if (clip == null) return; // no clip loaded yet

    if (!isPlaying) {
        clip.start();
        playPauseButton.setIcon(pauseIcon);
        isPlaying = true;
    } else {
        clip.stop();
        playPauseButton.setIcon(playIcon);
        isPlaying = false;
    }
        
}
    private void restartSong() {
    if (clip == null) return; // no song loaded

    clip.stop();                    // stop current playback
    clip.setMicrosecondPosition(0); // rewind to beginning
    clip.start();                   // play again

    playPauseButton.setIcon(pauseIcon);
    isPlaying = true;
}
    private void setVolume(int volume) {
    if (clip == null) return;

    try {
        FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float min = gain.getMinimum();
        float max = gain.getMaximum();

        float gainValue = min + (volume / 100f) * (max - min);
        gain.setValue(gainValue);
    } catch (Exception e) {
        System.out.println("Volume control not supported");
    }
}


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        playPauseButton = new javax.swing.JButton();
        restartButton = new javax.swing.JButton();
        titleName = new javax.swing.JLabel();
        titleIcon = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        songList = new javax.swing.JList<>();
        jProgressBar1 = new javax.swing.JProgressBar();
        Previous = new javax.swing.JButton();
        Next = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        volumeButton = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MUSIC PLAYER");
        setBackground(java.awt.Color.orange);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(900, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(600, 800));

        playPauseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/play.png"))); // NOI18N
        playPauseButton.setBorderPainted(false);
        playPauseButton.setContentAreaFilled(false);
        playPauseButton.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/pause.png"))); // NOI18N
        playPauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playPauseButtonActionPerformed(evt);
            }
        });

        restartButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rotate-reverse.png"))); // NOI18N
        restartButton.setBorderPainted(false);
        restartButton.setContentAreaFilled(false);
        restartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartButtonActionPerformed(evt);
            }
        });

        titleName.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        titleName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleName.setText("MUSIC PLAYER");

        titleIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/headphones-rhythm (1).png"))); // NOI18N

        songList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        songList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                songListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(songList);

        Previous.setIcon(new javax.swing.ImageIcon(getClass().getResource("/backward.png"))); // NOI18N
        Previous.setBorderPainted(false);
        Previous.setContentAreaFilled(false);
        Previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreviousActionPerformed(evt);
            }
        });

        Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/step-forward.png"))); // NOI18N
        Next.setBorderPainted(false);
        Next.setContentAreaFilled(false);
        Next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe Print", 0, 10)); // NOI18N
        jLabel1.setText(" Restart");

        volumeButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                volumeButtonStateChanged(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/volume+++.png"))); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(20, 20));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/volume-down.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe Print", 0, 10)); // NOI18N
        jLabel4.setText("Previous");

        jLabel5.setFont(new java.awt.Font("Segoe Print", 0, 10)); // NOI18N
        jLabel5.setText("Play/\nPause");

        jLabel6.setFont(new java.awt.Font("Segoe Print", 0, 10)); // NOI18N
        jLabel6.setText("Next");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(restartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(109, 109, 109)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Previous, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(playPauseButton))
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(Next, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(volumeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(435, 435, 435)
                        .addComponent(titleIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(titleName, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(titleName, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                    .addComponent(titleIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 537, Short.MAX_VALUE)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Next)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(Previous)
                                .addComponent(playPauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addGap(60, 60, 60))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(volumeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(restartButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)))
                        .addGap(32, 32, 32))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void playPauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playPauseButtonActionPerformed
        // TODO add your handling code here:
        togglePlayPause();
    }//GEN-LAST:event_playPauseButtonActionPerformed

    private void songListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_songListMouseClicked
        // TODO add your handling code here:
    int index = songList.getSelectedIndex();
    if (index != -1) {
        String selectedSong = songList.getModel().getElementAt(index);
        String folderPath = "D:\\Download\\Java Project\\Music player\\wav songs"; // same folder as loadSongs
        File selectedFile = new File(folderPath, selectedSong);
        jProgressBar1.setValue(0);  // Reset progress bar when a new song starts
        if (!selectedFile.exists()) {
            JOptionPane.showMessageDialog(this, "File not found: " + selectedFile.getAbsolutePath());
            return;
        }

        // If the selected song is different from currently playing
        if (currentFile == null || !currentFile.equals(selectedFile)) {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
            currentFile = selectedFile; // update current file
            playMusic();               // play new song
            playPauseButton.setIcon(pauseIcon);
            isPlaying = true;
        } else {
            // Same song clicked → toggle pause/resume
            togglePlayPause();
        }
        }
    }//GEN-LAST:event_songListMouseClicked

    private void restartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartButtonActionPerformed
        // TODO add your handling code here:
        restartSong();
    }//GEN-LAST:event_restartButtonActionPerformed

    private void NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextActionPerformed
        // TODO add your handling code here:
    int currentIndex = songList.getSelectedIndex();
    if (currentIndex == -1) return;

    int nextIndex = currentIndex + 1;

    // loop to first song if last
    if (nextIndex >= songList.getModel().getSize()) {
        nextIndex = 0;
    }

    playSongAtIndex(nextIndex);
    }//GEN-LAST:event_NextActionPerformed

    private void PreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreviousActionPerformed
        // TODO add your handling code here:
        int currentIndex = songList.getSelectedIndex();
        if (currentIndex == -1) return;

        int prevIndex = currentIndex - 1;

        // loop to last song if first
        if (prevIndex < 0) {
            prevIndex = songList.getModel().getSize() - 1;
        }

        playSongAtIndex(prevIndex);
    }//GEN-LAST:event_PreviousActionPerformed

    private void volumeButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_volumeButtonStateChanged
        // TODO add your handling code here:
        setVolume(volumeButton.getValue());
    }//GEN-LAST:event_volumeButtonStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new GUIMXPLAYER().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Next;
    private javax.swing.JButton Previous;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton playPauseButton;
    private javax.swing.JButton restartButton;
    private javax.swing.JList<String> songList;
    private javax.swing.JLabel titleIcon;
    private javax.swing.JLabel titleName;
    private javax.swing.JSlider volumeButton;
    // End of variables declaration//GEN-END:variables
}

