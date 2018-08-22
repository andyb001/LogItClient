package com.whittle.logit;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.MediaTracker;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import com.google.api.client.util.Base64;
import com.whittle.logit.dto.ItemDTO;
import com.whittle.logit.dto.ItemTypeDTO;

public class AddItemPanel extends JPanel {

	private static final int MAX_IMG_SIZE = 300;
	private static final long serialVersionUID = 1L;
	JTextField itemNameTextField = new JTextField();
	JTextArea descriptionTextArea = new JTextArea();
	JLabel imageLabel = new JLabel();
	JLabel barCodeImageLabel = new JLabel();
	JButton rotateButton = new JButton("Rotate");
	JComboBox<ItemTypeDTO> itemTypeComboBox = null;
	JButton saveButton = new JButton("Save");
	JTextField costTextField = new JTextField();

	public AddItemPanel() {
		init();
	}

	public AddItemPanel(LayoutManager arg0) {
		super(arg0);
		init();
	}

	public AddItemPanel(boolean arg0) {
		super(arg0);
		init();
	}

	public AddItemPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		init();
	}

	private void init() {
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		g.gridx = 0;
		g.gridy = 0;
		g.insets = new Insets(10, 10, 10, 10);
		g.anchor = GridBagConstraints.EAST;
		g.fill = GridBagConstraints.HORIZONTAL;
		add(new JLabel("Item Name"), g);
		g.gridx++;
		itemNameTextField.setPreferredSize(new Dimension(400, 30));
		itemNameTextField.setMinimumSize(new Dimension(400, 30));
		add(itemNameTextField, g);
		
		
		
		
		g.gridx++;
		g.gridheight = 4;
		imageLabel.setPreferredSize(new Dimension(MAX_IMG_SIZE, MAX_IMG_SIZE));
		imageLabel.setMinimumSize(new Dimension(MAX_IMG_SIZE, MAX_IMG_SIZE));
		imageLabel.setBorder(BorderFactory.createTitledBorder("Main Image"));
		add(imageLabel, g);
		
		g.gridheight = 1;
		g.gridy++;
		g.gridx = 0;
		
		add(new JLabel("Description"), g);
		
		g.gridx++;
		descriptionTextArea.setPreferredSize(new Dimension(400, 100));
		descriptionTextArea.setMinimumSize(new Dimension(400, 100));
		descriptionTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
		add(descriptionTextArea, g);
		
		g.gridy++;
		g.gridx = 0;
		add(new JLabel("Item Type"), g);
		g.gridx++;
		List<ItemTypeDTO> itemTypes = Cache.getInstance().getItemTypes();
		Vector<ItemTypeDTO> vec = new Vector<>();
		vec.addAll(itemTypes);
		itemTypeComboBox = new JComboBox<>(vec);
		itemTypeComboBox.setRenderer(new MyComboBoxRenderer());
		add(itemTypeComboBox, g);
		
		g.gridy++;
		g.gridx = 0;
		add(new JLabel("Cost"), g);
		g.gridx++;
		add(costTextField, g);
		
		imageLabel.setDropTarget(new DropTarget() {
			private static final long serialVersionUID = 1L;

			public synchronized void drop(DropTargetDropEvent evt) {
		        try {
		            evt.acceptDrop(DnDConstants.ACTION_COPY);
		            @SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
		            for (File file : droppedFiles) {
		                // process files
		            	System.out.println(file);
		            	URI uri = file.toURI();
		            	ImageIcon icon = new ImageIcon(uri.toURL());
		            	int w = icon.getIconWidth();
		            	int h = icon.getIconHeight();
		            	System.out.println("w = " + w + ", h= " + h);
		            	int wN, hN;
		            	if (w > h) {
		            		wN = MAX_IMG_SIZE;
		            		hN = (int) ((1.0*h/w) * new Double(MAX_IMG_SIZE));
		            	} else {
		            		hN = MAX_IMG_SIZE;
		            		wN = (int) ((1.0*w/h) * new Double(MAX_IMG_SIZE));
		            	}
		            	System.out.println("wN = " + wN + ", hN= " + hN);
		            	Image scaleImage = icon.getImage().getScaledInstance(wN, hN,Image.SCALE_SMOOTH);
		            	imageLabel.setIcon(new ImageIcon(scaleImage));
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		});
		
		g.gridy++;
		g.gridx++;
		add(rotateButton, g);
		
		rotateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MediaTracker mt = new MediaTracker(new JFrame());
				Image image = ((ImageIcon) imageLabel.getIcon()).getImage();
			    mt.addImage(image, 0);
			    try {
			      mt.waitForID(0);
			    } catch (InterruptedException ie) {
			    }
			    BufferedImage sourceBI = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			    
			    
			    Graphics2D g = (Graphics2D) sourceBI.getGraphics();
			    g.drawImage(image, 0, 0, null);

			    AffineTransform at = new AffineTransform();

			    // scale image
			    at.scale(1.0, 1.0);

			    // rotate 45 degrees around image center
			    at.rotate(90.0 * Math.PI / 180.0, sourceBI.getWidth(), sourceBI.getHeight());
			    
			    
			    AffineTransform translationTransform;
			    translationTransform = findTranslation(at, sourceBI);
			    at.preConcatenate(translationTransform);

			    // instantiate and apply affine transformation filter
			    BufferedImageOp bio = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

			    BufferedImage destinationBI = bio.filter(sourceBI, null);
			    
			    imageLabel.setIcon(new ImageIcon(destinationBI));
			}
		});
		
		g.gridy++;
		barCodeImageLabel.setPreferredSize(new Dimension(MAX_IMG_SIZE, 100));
		barCodeImageLabel.setMinimumSize(new Dimension(MAX_IMG_SIZE, 100));
		barCodeImageLabel.setBorder(BorderFactory.createTitledBorder("Bar Code"));
		
		barCodeImageLabel.setDropTarget(new DropTarget() {
			private static final long serialVersionUID = 1L;

			public synchronized void drop(DropTargetDropEvent evt) {
		        try {
		            evt.acceptDrop(DnDConstants.ACTION_COPY);
		            @SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
		            for (File file : droppedFiles) {
		                // process files
		            	System.out.println(file);
		            	URI uri = file.toURI();
		            	ImageIcon icon = new ImageIcon(uri.toURL());
		            	int w = icon.getIconWidth();
		            	int h = icon.getIconHeight();
		            	System.out.println("w = " + w + ", h= " + h);
		            	int wN, hN;
		            	if (w > h) {
		            		wN = MAX_IMG_SIZE;
		            		hN = (int) ((1.0*h/w) * new Double(MAX_IMG_SIZE));
		            	} else {
		            		hN = 100;
		            		wN = (int) ((1.0*w/h) * new Double(100));
		            	}
		            	System.out.println("wN = " + wN + ", hN= " + hN);
		            	Image scaleImage = icon.getImage().getScaledInstance(wN, hN,Image.SCALE_SMOOTH);
		            	barCodeImageLabel.setIcon(new ImageIcon(scaleImage));
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		});
		add(barCodeImageLabel, g);
		
		g.gridx = 0;
		g.gridy++;
		add(saveButton, g);
		
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveItem();
			}
		});
	}
	
	protected void saveItem() {
		
		ItemDTO itemDTO = new ItemDTO();
		
		itemDTO.setName(itemNameTextField.getText());
		itemDTO.setDescription(descriptionTextArea.getText());
		itemDTO.setCost(Double.valueOf(costTextField.getText()==""?"0.0":costTextField.getText()));
		itemDTO.setItemTypeId(((ItemTypeDTO)itemTypeComboBox.getSelectedItem()).getId());
		int response = 0;
		try {
			Icon icon = imageLabel.getIcon();
			BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			File outFile = new File("C:/tmp/out.jpg");
			FileOutputStream fos = new FileOutputStream(outFile);
			Graphics2D g2 = image.createGraphics();
			g2.drawImage(image, 0, 0, null);
			ImageIO.write(image, "jpg", fos);
			byte[] fileContent = Files.readAllBytes(outFile.toPath());
			String sTRBytes = Base64.encodeBase64URLSafeString(fileContent);
			itemDTO.setImageFileStrBytes(sTRBytes);
			
			icon = barCodeImageLabel.getIcon();
			if (icon != null) {
				image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
				fos = new FileOutputStream(outFile);
				
				//baos = new ByteArrayOutputStream();
				g2 = image.createGraphics();
				g2.drawImage(image, 0, 0, null);
				ImageIO.write(image, "jpg", fos);
				fileContent = Files.readAllBytes(outFile.toPath());
				sTRBytes = Base64.encodeBase64URLSafeString(fileContent);
				//imageInByte = baos.toByteArray();
				//System.out.println("Image 2: " + imageInByte.length);
				itemDTO.setBarCodeImageFileStrBytes(sTRBytes);
			}
			
			response = Service.saveItem(itemDTO);
			System.out.println("Response: " + response);
			
			if (response != 200) {
				JOptionPane.showMessageDialog(this, "Error saving Item.", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error saving Item... " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	private AffineTransform findTranslation(AffineTransform at, BufferedImage bi) {
	    Point2D p2din, p2dout;

	    p2din = new Point2D.Double(0.0, 0.0);
	    p2dout = at.transform(p2din, null);
	    double ytrans = p2dout.getY();

	    p2din = new Point2D.Double(0, bi.getHeight());
	    p2dout = at.transform(p2din, null);
	    double xtrans = p2dout.getX();

	    AffineTransform tat = new AffineTransform();
	    tat.translate(-xtrans, -ytrans);
	    return tat;
	 }

	
	private byte [] getImgBytes(Image image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(getBufferedImage(image), "JPEG", baos);
        } catch (IOException ex) {
            //handle it here.... not implemented yet...
        }
        return baos.toByteArray();
    }
	
	private BufferedImage getBufferedImage(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //Graphics2D g2d = bi.createGraphics();
        //g2d.drawImage(image, 0, 0, null);
        return bi;
    }
	
	class MyComboBoxRenderer extends JLabel implements ListCellRenderer<ItemTypeDTO> {

		@Override
		public Component getListCellRendererComponent(JList<? extends ItemTypeDTO> list, ItemTypeDTO value, int index,
				boolean isSelected, boolean cellHasFocus) {
			if (value != null) {
				setText(value.getName());
			}
			return this;
		}
		
	}
}
