import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableColumn;

/**
 * 
 */

/**
 * @author andrew.kerner
 *
 */
public class GUIShoppingCart extends JFrame implements ActionListener {
	private JFileChooser fileChooser; //allows file selection
	private JLabel outputLabel; //label for file contents
	private JLabel cartLabel; //label for shopping cart
	private JLabel removeBookLabel; //label for book removal spinner
	private JLabel buyNumBooksLabel; //label for buy book slider
	private JLabel costLabel; //label for total cost
	private JSpinner removeBookSelect; //choose book to remove from shopping cart
	private JSlider numBooks; //number of books to purchase
	private JButton openFileButton; //button to open file
	private JButton selectBook; //adds a book to shopping cart
	private JButton removeBook; //removes a book from shopping cart
	private JButton checkOut; //purchases items in shopping cart
	private JTextArea shoppingCart; //list of selected books
	private JTable bookList; //table that holds titles and cost
	private JScrollPane listScroller;
	private String[] columnHeadings;
	private String[][] tableContents;
	private JFormattedTextField costBox; //text field for total cost
	private int bookCounter = 0;
	private int x = 0;
	private int sliderVal; //multiplying variable for slider
	private ArrayList<Integer> sliderArray = new ArrayList<Integer>(); //subtracting multiplier
	private ArrayList<Integer> bookIndex = new ArrayList<Integer>();
	private double totalCost; //total cost of purchases
	private double bookCost; //cost of a single book
	private int spinnerVal; //removing value for spinner
	private ArrayList<String> bookCart = new ArrayList<String>();
	GridBagConstraints layoutConst = null;
	NumberFormat currency = NumberFormat.getCurrencyInstance();
	

	GUIShoppingCart() {
		//JSpinner variables
		int initRemoveBook = 1;
		int minRemove = 1;
		int maxRemove = 10;
		int stepVal = 1;
		
		//JSlider variables
		int buyMin = 1;
		int buyMax = 10;
		int buyInit = 1;
				
								
		//Create the JSpinner
		SpinnerNumberModel spinnerModel = null;
		spinnerModel = new SpinnerNumberModel(initRemoveBook, minRemove, maxRemove, stepVal);
		removeBookSelect = new JSpinner(spinnerModel);
		removeBookSelect.setEditor(new JSpinner.DefaultEditor(removeBookSelect));
		
		//Create the JSlider
		numBooks = new JSlider(buyMin, buyMax, buyInit);
		numBooks.setMajorTickSpacing(1);
		numBooks.setMinorTickSpacing(1);
		numBooks.setPaintTicks(true);
		numBooks.setPaintLabels(true);
		
		
		
		//Title of the window
		setTitle("Book purchaser");		
		
		outputLabel = new JLabel("Book list");
		cartLabel = new JLabel("Shopping Cart");
		removeBookLabel = new JLabel("Book # to remove");
		buyNumBooksLabel = new JLabel("Number of books to buy");
		costLabel = new JLabel("Total cost");
		
		
		bookList = new JTable(10,0);
		listScroller = new JScrollPane(bookList);
		
		openFileButton = new JButton("Open file");
		openFileButton.addActionListener(this);
		
		selectBook = new JButton("Add book to cart");
		selectBook.addActionListener(this);
		
		removeBook = new JButton("Remove book from cart");
		removeBook.addActionListener(this);
		
		checkOut = new JButton("Check out");
		checkOut.addActionListener(this);
		
		shoppingCart = new JTextArea(10, 10);
		listScroller = new JScrollPane(shoppingCart);
		shoppingCart.setEditable(false);
		
		
		costBox = new JFormattedTextField(currency);
		costBox.setEditable(false);
		costBox.setColumns(10);
		costBox.setValue(0);
		
		fileChooser = new JFileChooser();
		
		setLayout(new GridBagLayout());
		
		columnHeadings = new String[2];
		tableContents = new String[9][2];
		
				
		columnHeadings[0] = "Books";
		columnHeadings[1] = "Price";
		
				
		bookList = new JTable(tableContents, columnHeadings);
		
		layoutConst = new GridBagConstraints();
	      layoutConst.insets = new Insets(10, 0, 5, 0);
	      layoutConst.fill = GridBagConstraints.HORIZONTAL;
	      layoutConst.gridx = 0;
	      layoutConst.gridy = 0;
	      add(openFileButton, layoutConst);

	      layoutConst = new GridBagConstraints();
	      layoutConst.insets = new Insets(10, 0, 5, 1);
	      layoutConst.anchor = GridBagConstraints.CENTER;
	      layoutConst.gridx = 1;
	      layoutConst.gridy = 0;
	      add(selectBook, layoutConst);
	      
	      layoutConst = new GridBagConstraints();
	      layoutConst.insets = new Insets(0, 5, 60, 1);
	      layoutConst.anchor = GridBagConstraints.LINE_END;
	      layoutConst.gridx = 0;
	      layoutConst.gridy = 3;
	      add(removeBook, layoutConst);
	      
	      layoutConst = new GridBagConstraints();
	      layoutConst.insets = new Insets(10, 0, 5, 1);
	      layoutConst.anchor = GridBagConstraints.LINE_END;
	      layoutConst.gridx = 2;
	      layoutConst.gridy = 0;
	      add(checkOut, layoutConst);
	      
	      layoutConst = new GridBagConstraints();
	      layoutConst.insets = new Insets(50, 10, 0, 0);
	      layoutConst.fill = GridBagConstraints.HORIZONTAL;
	      layoutConst.gridx = 0;
	      layoutConst.gridy = 0;
	      add(outputLabel, layoutConst);
	      
	      layoutConst = new GridBagConstraints();
	      layoutConst.insets = new Insets(152, 10, 0, 0);
	      layoutConst.fill = GridBagConstraints.HORIZONTAL;
	      layoutConst.gridx = 0;
	      layoutConst.gridy = 1;
	      add(cartLabel, layoutConst);
	      
	      layoutConst = new GridBagConstraints();
	      layoutConst.insets = new Insets(1, 10, 10, 0);
	      layoutConst.fill = GridBagConstraints.HORIZONTAL;
	      layoutConst.gridx = 0;
	      layoutConst.gridy = 1;
	      layoutConst.gridwidth = 3;
	      add(bookList, layoutConst);
	      
	      layoutConst = new GridBagConstraints();
	      layoutConst.insets = new Insets(5, 10, 10, 10);
	      layoutConst.fill = GridBagConstraints.HORIZONTAL;
	      layoutConst.gridx = 0;
	      layoutConst.gridy = 2;
	      layoutConst.gridwidth = 2; 
	      add(listScroller, layoutConst);
	      
	      layoutConst = new GridBagConstraints();
	      layoutConst.insets = new Insets(5, 10, 10, 10);
	      layoutConst.fill = GridBagConstraints.HORIZONTAL;
	      layoutConst.gridx = 2;
	      layoutConst.gridy = 2;
	      layoutConst.gridwidth = 2; 
	      add(costLabel, layoutConst);
	      
	      layoutConst = new GridBagConstraints();
	      layoutConst.insets = new Insets(40, 10, 10, 10);
	      layoutConst.fill = GridBagConstraints.HORIZONTAL;
	      layoutConst.gridx = 2;
	      layoutConst.gridy = 2;
	      layoutConst.gridwidth = 2; 
	      add(costBox, layoutConst);
	      
	      layoutConst = new GridBagConstraints();
	      layoutConst.insets = new Insets(-5, 10, 10, 0);
	      layoutConst.fill = GridBagConstraints.HORIZONTAL;
	      layoutConst.gridx = 0;
	      layoutConst.gridy = 3;
	      add(removeBookLabel, layoutConst);
	      
	      layoutConst = new GridBagConstraints();
	      layoutConst.insets = new Insets(40, 10, 10, 0);
	      layoutConst.fill = GridBagConstraints.HORIZONTAL;
	      layoutConst.gridx = 0;
	      layoutConst.gridy = 3;
	      add(removeBookSelect, layoutConst);
	      
	      	      
	      layoutConst = new GridBagConstraints();
	      layoutConst.insets = new Insets(-45, 30, 10, 0);
	      layoutConst.fill = GridBagConstraints.HORIZONTAL;
	      layoutConst.gridx = 1;
	      layoutConst.gridy = 3;
	      add(buyNumBooksLabel, layoutConst);
	      
	      layoutConst = new GridBagConstraints();
	      layoutConst.insets = new Insets(10, 30, 10, 0);
	      layoutConst.fill = GridBagConstraints.HORIZONTAL;
	      layoutConst.gridx = 1;
	      layoutConst.gridy = 3;
	      add(numBooks, layoutConst);
	      
        
	  
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		FileInputStream fileByteStream = null; //File input stream
		Scanner inFS = null; 
		String readLine; //input from file
		File readFile = null; //Input file
		int fileChooserVal; // File chooser
		
		
		JButton sourceEvent = (JButton) event.getSource();
				
		if (sourceEvent == openFileButton) {
					
		
			// Open file chooser dialog and get the file to open
			fileChooserVal = fileChooser.showOpenDialog(this);
			
			//Check if file was selected
			if (fileChooserVal == JFileChooser.APPROVE_OPTION) {
				readFile = fileChooser.getSelectedFile();
				
				if (readFile.canRead()) {
					try {
						fileByteStream = new FileInputStream(readFile);
						inFS = new Scanner(fileByteStream);
						
						while (inFS.hasNext()) {
							readLine = inFS.nextLine();
							String [] bookArray = readLine.split(",");
							
							bookList.setValueAt(bookArray[0], bookCounter, 0);
							bookList.setValueAt(bookArray[1], bookCounter, 1);
												
							/*tableContents[bookCounter][0] = (bookArray[0]);
							tableContents[bookCounter][1] = (bookArray[1]);*/
							bookCounter += 1;
												
						}
																
					}
					catch (IOException e) {
						
					}
				}
				else {
					JOptionPane.showMessageDialog(this, "Error reading file");
				}
			}
		}
		
		else if (sourceEvent == selectBook) {
			x += 1;
			sliderVal = numBooks.getValue();
			sliderArray.add(sliderVal);
			int selectedRowIndex = bookList.getSelectedRow();
			bookIndex.add(selectedRowIndex);
			shoppingCart.setText("");
			bookCart.add(tableContents[selectedRowIndex][0] + " x" + sliderVal );
			bookCost = Double.parseDouble(tableContents[selectedRowIndex][1]);
				String formattedList = "";
				for (String book : bookCart) {
					formattedList = (formattedList + book + "\n");
					
				}
				totalCost += (sliderVal * bookCost);
				shoppingCart.append(formattedList);
				costBox.setValue(totalCost);
			
			
		}
		
		else if (sourceEvent == removeBook) {
			spinnerVal = (int) removeBookSelect.getValue();
			if (spinnerVal > bookIndex.size()) {
				JOptionPane.showMessageDialog(null, "No book to remove at this location.");
				
			}
			else {
				spinnerVal -= 1;
				int tempCostVal;
				int tempRemoveVal = bookIndex.get(spinnerVal);
				bookIndex.remove(spinnerVal);
				bookCart.remove(spinnerVal);
				shoppingCart.setText("");
					String formattedList = "";
					for (String book : bookCart) {
						formattedList = formattedList + book + "\n";
						}
					shoppingCart.append(formattedList);
				bookCost = Double.parseDouble(tableContents[tempRemoveVal][1]);
				tempCostVal = sliderArray.get(spinnerVal);
				sliderArray.remove(spinnerVal);
				totalCost -= (tempCostVal * bookCost);
				costBox.setValue(totalCost);
			}
			
		}
		
		else if (sourceEvent == checkOut) {
			int tax = 6;
			double totalTax = (totalCost + totalCost * (tax * .01));
			
			JOptionPane.showMessageDialog(null, "Thank you for your purchase. \n Your total purchase was " + currency.format(totalCost) + 
										" with a tax of " + tax + "% . \n Your grand total is " + currency.format(totalTax)
										+ " \n Please allow up to 3 years for delivery." );
			System.exit(0);
			
		}
		
		
	}
	public static void main(String[] args) {
		
		GUIShoppingCart shoppingList = new GUIShoppingCart();
				
		 shoppingList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     shoppingList.pack();
	     shoppingList.setVisible(true);
		
		

	}

	

}
