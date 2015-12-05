/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package KruskalMaze;

import java.util.Random;
import java.util.*;
import java.lang.Thread;

public class Demo extends javax.swing.JFrame {

    public static final int N = 1;
    public static final int S = 2;
    public static final int E = 4;
    public static final int W = 8;

    //public static final int MAX_WIDTH = 10;
    //public static final int MAX_HEIGHT = 10;

    protected Random random = null;
    private int seed; 
    private int w;
    private int h;
    protected int[][] grid = null;
    
    private List<List<Tree>> sets;
    private Stack<Edge> edges;
    
        
    public Demo() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mazePanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        seedTextField = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kruskal Maze Generator/Solver");

        mazePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout mazePanelLayout = new javax.swing.GroupLayout(mazePanel);
        mazePanel.setLayout(mazePanelLayout);
        mazePanelLayout.setHorizontalGroup(
            mazePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 680, Short.MAX_VALUE)
        );
        mazePanelLayout.setVerticalGroup(
            mazePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );

        jButton1.setText("Generate random");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("Generate with seed:");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Seed:");

        jTextField1.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mazePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(seedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(seedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(mazePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        seedTextField.getAccessibleContext().setAccessibleName("seedTextField");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        //  generate maze without a seed.
        generateMaze(-1);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // generate maze with seed entered in textbox.
        generateMaze(Integer.parseInt(seedTextField.getText()));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void generateMaze(int s)
    {
        System.out.println("Initilizing maze...");
       
        w = 5;
        h = 5;

        if(s >= 0) 
            seed = s;
        else {
            seed = new Random().nextInt(1000);
        }
        
        random = new Random(seed);
        jTextField1.setText(seed + "");
        
        grid = new int[h][w];
	for ( int j=0; j < h; ++j ) {
		for ( int i=0; i < w; ++i ) {
			grid[j][i] = 0;
		}
	}

	// draw test
        //draw();
        
        // fill sets list with tree types
        sets = new ArrayList<List<Tree>>();
	for ( int y=0; y < h; ++y ) {
		List<Tree> tmp = new ArrayList<Tree>();
		for ( int x=0; x < w; ++x ) {
			tmp.add(new Tree());
		}
		sets.add(tmp);
	}
		
        // Fill edge stack with new edges.
	edges = new Stack<Edge>();
	for ( int y=0; y < h; ++y ) {
		for (int x=0; x < w; ++x ) {
			if ( y > 0 ) 	{ edges.add(new Edge(x,y,N)); }
			if ( x > 0 ) 	{ edges.add(new Edge(x,y,W)); }
		}
	}
	
        for ( int i=0; i < edges.size(); ++i ) {
            int pos = random.nextInt(edges.size());
            Edge tmp1 = edges.get(i);
            Edge tmp2 = edges.get(pos);
            edges.set(i,tmp2);
            edges.set(pos,tmp1);
	}
  
        
        // kruskal
        while ( edges.size() > 0 ) {
            Edge tmp = edges.pop();
            int x = tmp.getX();
            int y = tmp.getY();
            int direction = tmp.getDirection();
            int dx = x + DX(direction), dy = y + DY(direction);
			
            Tree set1 = (sets.get(y)).get(x);
            Tree set2 = (sets.get(dy)).get(dx);
			
            if ( !set1.connected(set2) ) {		
                set1.connect(set2);
		grid[y][x] |= direction;
		grid[dy][dx] |= oppositeOf(direction);
            }
            //drawGraphics();
	}
 
        // draw test
        draw();
        drawGraphics();
        //for(int i = 0; i < grid.length; i++) {
        //    for(int j = 0; j < grid[0].length; j++)
        //    {
        //        System.out.print(grid[i][j] + " ");
        //    }
        //    System.out.println();
        //}
}
    public static int DX(int direction) {
		switch ( direction ) {
		case E:
			return +1;
		case W:
			return -1;
		case N:
		case S:
			return 0;
		}
		return -1;
	}

	public static int DY(int direction) {
		switch ( direction ) {
		case E:
		case W:
			return 0;
		case N:
			return -1;
		case S:
			return 1;
		}
		return -1;
	}

	public static int oppositeOf(int direction) {
		switch ( direction ) {
		case E:
			return W;
		case W:
			return E;
		case N:
			return S;
		case S:
			return N;
                }
		return -1;
	}
    
    public void draw() {
		// Draw the "top row" of the maze
		System.out.print((char)27);
		System.out.print(" ");
		for ( int i=0; i < grid.length - 1; ++i ) {
			System.out.print("_");
		}
		System.out.println("");
		
		// Step through the grid/maze, cell-by-cell
		for ( int y=0; y < grid[0].length; ++y ) {
			System.out.print("|");
			for ( int x=0; x < grid[0].length; ++x ) {
				System.out.print( ((grid[y][x] & S) != 0) ? " " : "_" );
				if ( (grid[y][x] & E) != 0 ) {
					System.out.print( (((grid[y][x] | grid[y][x+1]) & S) != 0) ? " " : "_" );
				} else {
					System.out.print("|");
				}
			}
			System.out.println("");
		}
    }
    
    private void drawGraphics()
    {
        //clear maze panel
        mazePanel.getGraphics().clearRect(0, 0, getWidth(), getHeight());
        //draw graphics
                int startX = 10;
                int startY = 10;
                int curX = 10;
                int curY = 0;
                mazePanel.getGraphics().drawLine(0, 0, 10, 10);
                for(int i=0; i < w; i++ ) {
                    curX = (startX + (i*10)) + 10;
                    mazePanel.getGraphics().drawLine(startX + (i*10), startY, curX, 10);
                    //try {
                    //    Thread.sleep(1000);                 //1000 milliseconds is one second.
                    //} catch(InterruptedException ex) {
                    //    Thread.currentThread().interrupt();
                    //}
		}
                mazePanel.getGraphics().drawString("*", curX, 10+8);
                
                curX = startX;
                curY = startY;
                for ( int y=0; y < h; ++y ) {
                        curX = startX;
                        curY += 10;
                        mazePanel.getGraphics().drawLine(curX, curY-10, curX, curY);
			for ( int x=0; x < grid[0].length; ++x ) {
                            
                                if((grid[y][x] & S) != 0) {
                                } else {
                                    mazePanel.getGraphics().drawLine(curX, curY, curX+10, curY);
                                }
                                curX += 10;
                                if ( (grid[y][x] & E) != 0 ) {
                                        if(((grid[y][x] | grid[y][x+1]) & S) != 0) {
                                        }
                                        else
                                        {
                                            mazePanel.getGraphics().drawLine(curX, curY, curX+10, curY);
                                        }
                                        curX += 10;
				} else {
                                        curX -= 10;
                                        mazePanel.getGraphics().drawLine(curX, curY-10, curX, curY);
				}   
			}
		}
    }
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Demo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Demo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Demo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Demo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Demo().setVisible(true);
            }
        });
    }
    
    class Tree {
	private Tree parent = null;
	public Tree() {
		
	}
	public Tree root() {
		return parent != null ? parent.root() : this;
	}
	public boolean connected(Tree tree) {
		return this.root() == tree.root();
	}
        
	public void connect(Tree tree) {
		tree.root().setParent(this);
	}

	public void setParent(Tree parent) {
		this.parent = parent;
	}
    }
    
    class Edge {
	private int x;
	private int y;
	private int direction;
	
	public Edge(int x, int y, int direction) {
		this.x = x; 
		this.y = y;
		this.direction = direction;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getDirection() { return direction; }
}
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel mazePanel;
    private javax.swing.JTextField seedTextField;
    // End of variables declaration//GEN-END:variables
}
