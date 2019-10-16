package com.client;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
//

public class SelecTry implements ListSelectionListener
{
    clientelem clientElem = new clientelem();
    public void valueChanged(ListSelectionEvent e){
        clientElem.getMessage().setText(clientElem.getUserNameList().getSelectedValue()+"@");
    }
}
