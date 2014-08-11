package com.google.gwt.sample.stockwatcher.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dev.asm.Label;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class StockWatcher implements EntryPoint {

private VerticalPanel mainPanel = new VerticalPanel(); 
private FlexTable stocksFlexTable = new FlexTable(); 
private HorizontalPanel addPanel = new HorizontalPanel();
private TextBox newSymbolTextBox = new TextBox(); 
private TextBox newSymbolTextBox2 = new TextBox();
private Button addStockButton = new Button("Add"); 
//private Label lastUpdatedLabel = new Label();

private ArrayList<String> stocks = new ArrayList<String>();

/**  * Entry point method.  */  
public void onModuleLoad()
{  stocksFlexTable.setText(0, 0, "Item");
stocksFlexTable.setText(0, 1, "Price"); 
stocksFlexTable.setText(0, 2, "Change");
stocksFlexTable.setText(0, 3, "Remove");


// Add styles to elements in the stock list table.
stocksFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
stocksFlexTable.addStyleName("watchList");
stocksFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
stocksFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
stocksFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");


// Assemble Add Stock panel.
addPanel.add(newSymbolTextBox);
addPanel.add(newSymbolTextBox2);
addPanel.add(addStockButton);
addPanel.addStyleName("addPanel");
// Assemble Main panel.
mainPanel.add(stocksFlexTable);
mainPanel.add(addPanel);
//mainPanel.add(lastUpdatedLabel);

// Associate the Main panel with the HTML host page.
RootPanel.get("stockList").add(mainPanel);

// Move cursor focus to the input box.
newSymbolTextBox.setFocus(true);


// Listen for mouse events on the Add button.
addStockButton.addClickHandler(new ClickHandler() {
  public void onClick(ClickEvent event) {
    addStock();
  }
});

}

/**
* Add stock to FlexTable. Executed when the user clicks the addStockButton or
* presses enter in the newSymbolTextBox.
*/
private void addStock() {
    final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
    final String symbol2 = newSymbolTextBox2.getText().trim();
    
    newSymbolTextBox.setFocus(true);

    // Stock code must be between 1 and 10 chars that are numbers, letters, or dots.
    if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
      Window.alert("'" + symbol + "' is not a valid symbol.");
      newSymbolTextBox.selectAll();
      return;
    }

    newSymbolTextBox.setText("");
    newSymbolTextBox2.setText("");
    
    // Don't add the stock if it's already in the table.
    if (stocks.contains(symbol))
      return;
    
    // Add the stock to the table.
    int row = stocksFlexTable.getRowCount();
    stocks.add(symbol);
    stocks.add(symbol2);
    
    stocksFlexTable.setText(row, 0, symbol);
    stocksFlexTable.setText(row, 1, symbol2);
    
    stocksFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
    stocksFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
    stocksFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");
   
    // Add a button to remove this stock from the table.
    Button removeStockButton = new Button("x");
    removeStockButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        int removedIndex = stocks.indexOf(symbol);
        stocks.remove(removedIndex);        stocksFlexTable.removeRow(removedIndex + 1);
      }
    });
    stocksFlexTable.setWidget(row, 3, removeStockButton);

    // TODO Get the stock price.
}

}

