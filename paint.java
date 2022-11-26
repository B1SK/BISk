package com.paint;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
public class ImageEdit {
    int  mode = 0, xPad, xf, yf, yPad;

    boolean pressed=false;
    Color mainColor;
    MyFrame f;
    MyPanel panel;
    JButton colorButton;
    JColorChooser tcc;
    BufferedImage image;
    boolean loading=false;
    String fileName;
    JMenuItem savesMenu;
    public ImageEdit() {
        f= new MyFrame("Графический редактор");
        f.setSize(640,480);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainColor=Color.black;

        JMenuBar menuBar = new  JMenuBar();
        f.setJMenuBar(menuBar);
        menuBar.setBounds(0,0,350,30);
        JMenu fileMenu = new  JMenu("Файл");
        menuBar.add(fileMenu);

        Action loadAction = new  AbstractAction("Загрузить") {
            public void actionPerformed(ActionEvent event) {
                JFileChooser jf= new  JFileChooser();
                int  result = jf.showOpenDialog(null);
                if(result==JFileChooser.APPROVE_OPTION) {
                    try {
                        fileName = jf.getSelectedFile().getAbsolutePath();
                        File iF= new  File(fileName);
                        jf.addChoosableFileFilter(new TextFileFilter(".png"));
                        jf.addChoosableFileFilter(new TextFileFilter(".jpg"));
                        image = ImageIO.read(iF);
                        loading=true;
                        f.setSize(image.getWidth()+40, image.getWidth()+80);
                        panel.setSize(image.getWidth(), image.getWidth());
                        panel.repaint();
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(f, "Такого файла не существует");
                    }
                    catch (IOException ex) {
                        JOptionPane.showMessageDialog(f, "Исключение ввода-вывода");
                    }
                    catch (Exception ignored) {
                    }
                }
            }
        };
        JMenuItem loadMenu = new  JMenuItem(loadAction);
        fileMenu.add(loadMenu);

        Action saveAction = new  AbstractAction("Сохранить") {
            public void actionPerformed(ActionEvent event) {
                try {
                    JFileChooser jf= new  JFileChooser();
                    TextFileFilter pngFilter = new TextFileFilter(".png");
                    TextFileFilter jpgFilter = new TextFileFilter(".jpg");
                    if(fileName==null) {
                        jf.addChoosableFileFilter(pngFilter);
                        jf.addChoosableFileFilter(jpgFilter);
                        int  result = jf.showSaveDialog(null);
                        if(result==JFileChooser.APPROVE_OPTION) {
                            fileName = jf.getSelectedFile().getAbsolutePath();
                        }
                    }
                    if(jf.getFileFilter()==pngFilter) {
                        ImageIO.write(image, "png", new  File(fileName+".png"));
                    }
                    else {
                        ImageIO.write(image, "jpeg", new  File(fileName+".jpg"));
                    }
                }
                catch(IOException ex) {
                    JOptionPane.showMessageDialog(f, "Ошибка ввода-вывода");
                }
            }
        };

        JMenuItem saveMenu = new  JMenuItem(saveAction);
        fileMenu.add(saveMenu);

        Action savesAction = new  AbstractAction("Сохранить как...") {
            public void actionPerformed(ActionEvent event) {
                try {
                    JFileChooser jf= new  JFileChooser();
                    TextFileFilter pngFilter = new TextFileFilter(".png");
                    TextFileFilter jpgFilter = new TextFileFilter(".jpg");
                    jf.addChoosableFileFilter(pngFilter);
                    jf.addChoosableFileFilter(jpgFilter);
                    int  result = jf.showSaveDialog(null);
                    if(result==JFileChooser.APPROVE_OPTION) {
                        fileName = jf.getSelectedFile().getAbsolutePath();
                    }
                    if(jf.getFileFilter()==pngFilter) {
                        ImageIO.write(image, "png", new  File(fileName+".png"));
                    }
                    else {
                        ImageIO.write(image, "jpeg", new  File(fileName+".jpg"));
                    }
                }
                catch(IOException ex) {
                    JOptionPane.showMessageDialog(f, "Ошибка ввода-вывода");
                }
            }
        };

        savesMenu = new  JMenuItem(savesAction);
        fileMenu.add(savesMenu);

        panel = new  MyPanel();
        panel.setBounds(30,30,260,260);
        panel.setBackground(Color.white);
        panel.setOpaque(true);
        f.add(panel);

        JToolBar toolbar = new  JToolBar("Toolbar", JToolBar.VERTICAL);

        JButton penButton = new  JButton(new  ImageIcon("src/com/paint/images/pen.png"));
        penButton.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mode = 0;
            }
        });
        toolbar.add(penButton);
        JButton brushButton = new  JButton(new  ImageIcon("src/com/paint/images/brush.png"));
        brushButton.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mode = 1;
            }
        });
        toolbar.add(brushButton);

        JButton lasticButton = new JButton(new  ImageIcon("src/com/paint/images/lastic.png"));
        lasticButton.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mode = 2;
            }
        });
        toolbar.add(lasticButton);

        JButton textButton = new  JButton(new  ImageIcon("src/com/paint/images/text.png"));
        textButton.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mode = 3;
            }
        });
        toolbar.add(textButton);

        JButton lineButton = new  JButton(new  ImageIcon("src/com/paint/images/line.png"));
        lineButton.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mode = 4;
            }
        });
        toolbar.add(lineButton);

        JButton fillButton = new JButton(new ImageIcon("src/com/paint/images/colorbotle.png"));
        fillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mode = 5;
            }
        });
        toolbar.add(fillButton);


        toolbar.setBounds(0, 0, 30, 300);
        f.add(toolbar);

        JToolBar colorBar = new  JToolBar("ColorBar", JToolBar.HORIZONTAL);
        colorBar.setBounds(30, 0, 260, 30);
        colorButton = new  JButton();
        colorButton.setBackground(mainColor);
        colorButton.setBounds(15, 5, 20, 20);
        colorButton.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ColorDialog colorDI = new  ColorDialog(f,"Выбор цвета");
                colorDI.setVisible(true);
            }
        });
        colorBar.add(colorButton);


        JButton greenButton = new  JButton();
        greenButton.setBackground(Color.green);
        greenButton.setBounds(50, 5, 16, 16);
        greenButton.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mainColor = Color.green;
                colorButton.setBackground(mainColor);
            }
        });
        colorBar.add(greenButton);

        JButton blueButton = new JButton();
        blueButton.setBackground(Color.blue);
        blueButton.setBounds(70, 5, 16, 16);
        blueButton.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mainColor = Color.blue;
                colorButton.setBackground(mainColor);
            }
        });
        colorBar.add(blueButton);

        JButton cyanButton = new  JButton();
        cyanButton.setBackground(Color.cyan);
        cyanButton.setBounds(90, 5, 16, 16);
        cyanButton.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mainColor = Color.cyan;
                colorButton.setBackground(mainColor);
            }
        });
        colorBar.add(cyanButton);

        JButton magentaButton = new  JButton();
        magentaButton.setBackground(Color.magenta);
        magentaButton.setBounds(110, 5, 16, 16);
        magentaButton.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mainColor = Color.magenta;
                colorButton.setBackground(mainColor);
            }
        });
        colorBar.add(magentaButton);

        JButton whiteButton = new  JButton();
        whiteButton.setBackground(Color.white);
        whiteButton.setBounds(130, 5, 16, 16);
        whiteButton.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mainColor = Color.white;
                colorButton.setBackground(mainColor);
            }
        });
        colorBar.add(whiteButton);

        JButton blackButton = new  JButton();
        blackButton.setBackground(Color.black);
        blackButton.setBounds(150, 5, 16, 16);
        blackButton.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent event) {
                mainColor = Color.black;
                colorButton.setBackground(mainColor);
            }
        });
        colorBar.add(blackButton);
        colorBar.setLayout(null);
        f.add(colorBar);

        tcc = new  JColorChooser(mainColor);
        tcc.getSelectionModel().addChangeListener(new  ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                mainColor = tcc.getColor();
                colorButton.setBackground(mainColor);
            }
        });
        panel.addMouseMotionListener(new  MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (pressed) {
                    Graphics g = image.getGraphics();
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setColor(mainColor);
                    switch (mode) {
                        case 0 -> g2.drawLine(xPad, yPad, e.getX(), e.getY());
                        case 1 -> {
                            g2.setStroke(new BasicStroke(3.0f));
                            g2.drawLine(xPad, yPad, e.getX(), e.getY());
                        }
                        case 2 -> {
                            g2.setStroke(new BasicStroke(3.0f));
                            g2.setColor(Color.WHITE);
                            g2.drawLine(xPad, yPad, e.getX(), e.getY());
                        }
                    }
                    xPad=e.getX();
                    yPad=e.getY();
                }
                panel.repaint();
            }
        });
        panel.addMouseListener(new  MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                Graphics g = image.getGraphics();
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(mainColor);
                switch (mode) {
                    case 0 -> g2.drawLine(xPad, yPad, xPad + 1, yPad + 1);
                    case 1 -> {
                        g2.setStroke(new BasicStroke(3.0f));
                        g2.drawLine(xPad, yPad, xPad + 1, yPad + 1);
                    }
                    case 2 -> {
                        g2.setStroke(new BasicStroke(3.0f));
                        g2.setColor(Color.WHITE);
                        g2.drawLine(xPad, yPad, xPad + 1, yPad + 1);
                    }
                    case 3 -> panel.requestFocus();
                }
                xPad=e.getX();
                yPad=e.getY();

                pressed=true;
                panel.repaint();
            }
            public void mousePressed(MouseEvent e) {
                xPad=e.getX();
                yPad=e.getY();
                xf=e.getX();
                yf=e.getY();
                pressed=true;
            }
            public void mouseReleased(MouseEvent e) {

                Graphics g = image.getGraphics();
                Graphics2D g2 = (Graphics2D)g;
                g2.setColor(mainColor);
                if (mode == 4) {
                    g.drawLine(xf, yf, e.getX(), e.getY());
                }
                else if (mode == 5) {
                    g2.fillPolygon(int[] xf, int[]yf);
                }
                xf=0; yf=0;
                pressed=false;
                panel.repaint();
            }
        });
        panel.addKeyListener(new  KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                panel.requestFocus();
            }
            public void keyTyped(KeyEvent e) {
                if(mode == 3){
                    Graphics g = image.getGraphics();
                    Graphics2D g2 = (Graphics2D)g;
                    g2.setColor(mainColor);
                    g2.setStroke(new  BasicStroke(2.0f));

                    String str = new  String("");
                    str +=e.getKeyChar();
                    g2.setFont(new  Font("Arial", Font.PLAIN, 15));
                    g2.drawString(str, xPad, yPad);
                    xPad+=10;
                    panel.requestFocus();
                    panel.repaint();
                }
            }
        });
        f.addComponentListener(new  ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                if(!loading) {
                    panel.setSize(f.getWidth()-40, f.getHeight()-80);
                    BufferedImage tempImage = new  BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D d2 = (Graphics2D) tempImage.createGraphics();
                    d2.setColor(Color.white);
                    d2.fillRect(0, 0, panel.getWidth(), panel.getHeight());
                    tempImage.setData(image.getRaster());
                    image=tempImage;
                    panel.repaint();
                }
                loading=false;
            }
        });
        f.setLayout(null);
        f.setVisible(true);
    }

    class ColorDialog extends JDialog {
        public ColorDialog(JFrame owner, String title) {
            super(owner, title, true);
            add(tcc);
            setSize(200, 200);
        }
    }

    static class MyFrame extends JFrame {
        public void paint(Graphics g) {
            super.paint(g);
        }
        public MyFrame(String title) {
            super(title);
        }
    }

    class MyPanel extends JPanel {
        public MyPanel(){
        }
        public void paintComponent (Graphics g) {
            if(image==null) {
                image = new  BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D d2 = (Graphics2D) image.createGraphics();
                d2.setColor(Color.white);
                d2.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
            super.paintComponent(g);
            g.drawImage(image, 0, 0,this);
        }
    }
    static class TextFileFilter extends FileFilter {
        private final String ext;
        public TextFileFilter(String ext) {
            this.ext=ext;
        }
        public boolean accept(java.io.File file) {
            if (file.isDirectory()) return true;
            return (file.getName().endsWith(ext));
        }
        public String getDescription() {
            return "*"+ext;
        }
    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(new  Runnable() {
            public void run() {
                new  ImageEdit();
            }
        });
    }
}
