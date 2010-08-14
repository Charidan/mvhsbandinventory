package mvhsbandinventory;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author nicholson
 */
public class Display extends javax.swing.JPanel implements java.beans.Customizer, ListSelectionListener
{

    private Object bean;
    private InstrumentList instruments;
    private HistoryTableModel histModel;
    private JComboBox contains;
    private ContractGenerator conGen;
    private boolean detailChange;
    private boolean histChange;
    private int lastSelect = 0;
    
    /** Creates new customizer DispTest */
    public Display(InstrumentList instruments)
    {
        this.instruments = instruments;
        histModel = new HistoryTableModel(this);
        contains = new JComboBox();
        contains.addItem("Contains");
        conGen = new ContractGenerator();
        initComponents();

        for(String s : Instrument.attributes)
        {
            sortCombo.addItem(s);
        }

        advsearchResetButtonActionPerformed(null);

        // Set up the instrument table to have the appropriate responses to user
        // interaction (the right side adjusts to show the stuff on the left) 
        instruTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        instruTable.getSelectionModel().addListSelectionListener(this);

        // If there are instruments in the list, sort them appropriately
        sort();
        setSelectedIndex(0);
    }

    public Instrument getSelectedInstrument()
    {
        try
        {
            int i = instruTable.getSelectedRow();

            if(i==-1)
            {
                if(!instruments.isTableEmpty())
                {
                    instruTable.setRowSelectionInterval(0, 0);
                    i = 0;
                } else
                {
                    return Instrument.NULL_INSTRUMENT;
                }
            }

            return instruments.get((String) instruTable.getValueAt(i, 0),
                    (String) instruTable.getValueAt(i, 1),
                    (String) instruTable.getValueAt(i, 2));

        } catch(Exception e)
        {
            return Instrument.NULL_INSTRUMENT;
        }
    }

    public int getSelectedIndex()
    {
        Instrument instrument = getSelectedInstrument();
        return instruments.displayIndexOf(instrument);
    }

    private void setSelectedIndex(int index)
    {
        int row = (index==-1) ? 0 : index;

        if(!instruments.isTableEmpty())
        {
            instruTable.setRowSelectionInterval(row, row);
        }
    }

    public void setSelectedInstrument(Instrument instrument)
    {
        int index = instruments.displayIndexOf(instrument);

        setSelectedIndex(index);
    }

    public void valueChanged(ListSelectionEvent e)
    {
        if(e.getSource()==instruTable.getSelectionModel()
                &&instruTable.getRowSelectionAllowed())
        {
            if(detailChange||histChange)
            {
                int action = JOptionPane.showConfirmDialog(jopDialog, "Do you wish to save the changes to this instrument?", "Unsaved Data Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                switch(action)
                {
                    case JOptionPane.YES_OPTION:
                    {
                        Instrument instru = instruments.get(lastSelect);
                        instru.addHistory("Instrument saved.");
                        saveDetails(instru);
                        saveHistory(instru);
                        lastSelect = getSelectedIndex();
                        return;
                    }
                    case JOptionPane.CLOSED_OPTION:
                    {
                        instruTable.changeSelection(lastSelect, 0, false, false);
                        return;
                    }
                    case JOptionPane.CANCEL_OPTION:
                    {
                        boolean d = detailChange;
                        boolean h = histChange;
                        detailChange = false;
                        histChange = false;
                        instruTable.changeSelection(lastSelect, 0, false, false);
                        detailChange = d;
                        histChange = h;
                        return;
                    }
                    case JOptionPane.NO_OPTION:
                    {
                        displayInstrument();
                        lastSelect = getSelectedIndex();
                        detailChange = false;
                        histChange = false;
                        return;
                    }
                }
            } else
            {
                displayInstrument();
                lastSelect = getSelectedIndex();
            }
        }
    }

    public void saveDetails(Instrument instru)
    {
        if(!instru.isValid())
        {
            JOptionPane.showMessageDialog(jopDialog, "Error: Attempt to save a NULL_INSTRUMENT.", "Save Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try
        {
            instru.set("Rank", rankBox.getText());
            instru.set("Value", valueBox.getText());
            instru.set("Status", (String) statusCombo.getSelectedItem());
            instru.set("Ligature", (String) ligCombo.getSelectedItem());
            instru.set("Mouthpiece", (String) mpieceCombo.getSelectedItem());
            instru.set("MouthpieceCap", (String) capCombo.getSelectedItem());
            instru.set("Bow", (String) bowCombo.getSelectedItem());
            instru.set("NeckStrap", (String) strapCombo.getSelectedItem());
            instru.set("Notes", notesTPane.getText());
            instruments.update(instru);
            detailChange = false;
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(jopDialog,
                    "An Error has occurred while saving the instrument:\n"+ex.getMessage(),
                    "Save Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void displayInstrument()
    {
        Instrument instru = getSelectedInstrument();

        //set the Details panel
        if(instru.get("Status").equals(""))
        {
            statusCombo.setSelectedIndex(0);
        } else
        {
            statusCombo.setSelectedItem((String) instru.get("Status"));
        }

        instruBox.setText((String) instru.get("Instrument"));
        brandBox.setText((String) instru.get("Brand"));
        serialBox.setText((String) instru.get("Serial"));
        rankBox.setText((String) instru.get("Rank"));
        valueBox.setText((String) instru.get("Value"));

        if(instru.get("NeckStrap").equals(""))
        {
            strapCombo.setSelectedIndex(0);
        } else
        {
            strapCombo.setSelectedItem((String) instru.get("NeckStrap"));
        }
        if(instru.get("Ligature").equals(""))
        {
            ligCombo.setSelectedIndex(0);
        } else
        {
            ligCombo.setSelectedItem((String) instru.get("Ligature"));
        }
        if(instru.get("Mouthpiece").equals(""))
        {
            mpieceCombo.setSelectedIndex(0);
        } else
        {
            mpieceCombo.setSelectedItem((String) instru.get("Mouthpiece"));
        }
        if(instru.get("MouthpieceCap").equals(""))
        {
            capCombo.setSelectedIndex(0);
        } else
        {
            capCombo.setSelectedItem((String) instru.get("MouthpieceCap"));
        }
        if(instru.get("Bow").equals(""))
        {
            bowCombo.setSelectedIndex(0);
        } else
        {
            bowCombo.setSelectedItem((String) instru.get("Bow"));
        }

        notesTPane.setText((String) instru.get("Notes"));

        //set the History panel
        renterBox.setText((String) instru.get("Renter"));
        schoolyearBox.setText((String) instru.get("SchoolYear"));
        dateoutBox.setText((String) instru.get("DateOut"));

        if(instru.get("Fee").equals(""))
        {
            feeCombo.setSelectedIndex(1);
        } else
        {
            feeCombo.setSelectedItem((String) instru.get("Fee"));
        }
        if(instru.get("Period").equals(""))
        {
            periodCombo.setSelectedIndex(0);
        } else
        {
            periodCombo.setSelectedItem((String) instru.get("Period"));
        }
        if(instru.get("Contract").equals(""))
        {
            contractCombo.setSelectedIndex(0);
        } else
        {
            contractCombo.setSelectedItem((String) instru.get("Contract"));
        }

        otherBox.setText((String) instru.get("Other"));

        //set the History table
        histModel.fireTableChanged(null);
    }

    public void saveHistory(Instrument instru)
    {
        if(!instru.isValid())
        {
            JOptionPane.showMessageDialog(jopDialog, "Error: Attempt to save a NULL_INSTRUMENT.", "Save Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try
        {
            instru.set("Renter", renterBox.getText());
            instru.set("SchoolYear", schoolyearBox.getText());
            instru.set("DateOut", dateoutBox.getText());
            instru.set("Fee", (String) feeCombo.getSelectedItem());
            instru.set("Period", (String) periodCombo.getSelectedItem());
            instru.set("Other", otherBox.getText());
            instru.set("Status", (String) statusCombo.getSelectedItem());
            instruments.update(instru);
            histChange = false;
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(jopDialog,
                    "An Error has occurred while saving the instrument:\n"+ex.getMessage(),
                    "Save Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public InstrumentAttributeMatcher jcomps2matcher(JComboBox contains, JComboBox attribute, JTextField value)
    {
        return new InstrumentAttributeMatcher((String) attribute.getSelectedItem(), value.getText(), contains.getSelectedItem().equals("Contains"));
    }

    public void setObject(Object bean)
    {
        this.bean = bean;
    }

    private void sort()
    {
        String s = (String) sortCombo.getSelectedItem();
        instruments.sort(s, sortReverseCombo.getSelectedIndex()==0);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        advsearchAddFieldButton = new javax.swing.JButton();
        advsearchButtonPanel = new javax.swing.JPanel();
        advsearchSearchButton = new javax.swing.JButton();
        advsearchResetButton = new javax.swing.JButton();
        advsearchCancelButton = new javax.swing.JButton();
        jopDialog = new javax.swing.JDialog();
        advsearchDialog = new javax.swing.JDialog();
        advsearchPanel = new javax.swing.JPanel();
        addDialog = new javax.swing.JDialog();
        addTextLabel = new javax.swing.JLabel();
        addTypeLabel = new javax.swing.JLabel();
        addTypeBox = new javax.swing.JTextField();
        addBrandLabel = new javax.swing.JLabel();
        addBrandBox = new javax.swing.JTextField();
        addSerialLabel = new javax.swing.JLabel();
        addSerialBox = new javax.swing.JTextField();
        addButtonPanel = new javax.swing.JPanel();
        addAcceptButton = new javax.swing.JButton();
        addCancelButton = new javax.swing.JButton();
        overlord = new javax.swing.JSplitPane();
        leftsplitPanel = new javax.swing.JPanel();
        leftsplitFilterButtonPanel = new javax.swing.JPanel();
        advSearchButton = new javax.swing.JButton();
        showallButton = new javax.swing.JButton();
        excelButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        leftsplitSortButtonPanel = new javax.swing.JPanel();
        sortLabel = new javax.swing.JLabel();
        sortCombo = new javax.swing.JComboBox();
        sortReverseCombo = new javax.swing.JComboBox();
        sortButton = new javax.swing.JButton();
        leftsplitSortByPanel = new javax.swing.JPanel();
        leftsplitinstruTablePane = new javax.swing.JScrollPane();
        instruTable = new javax.swing.JTable();
        leftsplitaddButtonPanel = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        rightsplitPanel = new javax.swing.JPanel();
        infoTabs = new javax.swing.JTabbedPane();
        detailPanel = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        statusCombo = new javax.swing.JComboBox();
        instrumentLabel = new javax.swing.JLabel();
        instruBox = new javax.swing.JTextField();
        brandLabel = new javax.swing.JLabel();
        brandBox = new javax.swing.JTextField();
        serialLabel = new javax.swing.JLabel();
        serialBox = new javax.swing.JTextField();
        rankLabel = new javax.swing.JLabel();
        rankBox = new javax.swing.JTextField();
        valueLabel = new javax.swing.JLabel();
        valueBox = new javax.swing.JTextField();
        strapLabel = new javax.swing.JLabel();
        strapCombo = new javax.swing.JComboBox();
        ligatureLabel = new javax.swing.JLabel();
        ligCombo = new javax.swing.JComboBox();
        mpieceLabel = new javax.swing.JLabel();
        mpieceCombo = new javax.swing.JComboBox();
        capLabel = new javax.swing.JLabel();
        capCombo = new javax.swing.JComboBox();
        bowLabel = new javax.swing.JLabel();
        bowCombo = new javax.swing.JComboBox();
        noteLabel = new javax.swing.JLabel();
        detailNotePanel = new javax.swing.JScrollPane();
        notesTPane = new javax.swing.JTextPane();
        historyPanel = new javax.swing.JPanel();
        historySplit = new javax.swing.JSplitPane();
        historyTablePanel = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        checkoutPanel = new javax.swing.JPanel();
        renterLabel = new javax.swing.JLabel();
        renterBox = new javax.swing.JTextField();
        schoolyearLabel = new javax.swing.JLabel();
        schoolyearBox = new javax.swing.JTextField();
        dateoutLabel = new javax.swing.JLabel();
        dateoutBox = new javax.swing.JTextField();
        periodLabel = new javax.swing.JLabel();
        periodCombo = new javax.swing.JComboBox();
        feeLabel = new javax.swing.JLabel();
        feeCombo = new javax.swing.JComboBox();
        contractLabel = new javax.swing.JLabel();
        contractCombo = new javax.swing.JComboBox();
        otherLabel = new javax.swing.JLabel();
        otherBox = new javax.swing.JTextField();
        checkoutButtonPanel = new javax.swing.JPanel();
        formButton = new javax.swing.JButton();
        checkoutButton = new javax.swing.JButton();
        checkinButton = new javax.swing.JButton();
        lostButton = new javax.swing.JButton();
        rightsplitButtonPanel = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

        advsearchAddFieldButton.setText("Add Search Field");
        advsearchAddFieldButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                advsearchAddFieldButtonActionPerformed(evt);
            }
        });

        advsearchSearchButton.setText("SEARCH");
        advsearchSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                advsearchSearchButtonActionPerformed(evt);
            }
        });
        advsearchButtonPanel.add(advsearchSearchButton);

        advsearchResetButton.setText("RESET");
        advsearchResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                advsearchResetButtonActionPerformed(evt);
            }
        });
        advsearchButtonPanel.add(advsearchResetButton);

        advsearchCancelButton.setText("CANCEL");
        advsearchCancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                advsearchCancelButtonActionPerformed(evt);
            }
        });
        advsearchButtonPanel.add(advsearchCancelButton);

        jopDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jopDialog.getContentPane().setLayout(new java.awt.GridBagLayout());

        advsearchDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        advsearchDialog.setTitle("Advanced Search");
        advsearchDialog.setMinimumSize(new java.awt.Dimension(470, 150));
        advsearchDialog.getContentPane().setLayout(new java.awt.GridBagLayout());

        advsearchPanel.setLayout(new java.awt.GridBagLayout());
        advsearchDialog.getContentPane().add(advsearchPanel, new java.awt.GridBagConstraints());

        addDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addDialog.setTitle("ADD NEW INSTRUMENT");
        addDialog.setMinimumSize(new java.awt.Dimension(310, 200));
        addDialog.getContentPane().setLayout(new java.awt.GridBagLayout());

        addTextLabel.setText("Enter Instrument Characteristics");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipady = 5;
        addDialog.getContentPane().add(addTextLabel, gridBagConstraints);

        addTypeLabel.setText("Instrument:");
        addDialog.getContentPane().add(addTypeLabel, new java.awt.GridBagConstraints());

        addTypeBox.setColumns(20);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        addDialog.getContentPane().add(addTypeBox, gridBagConstraints);

        addBrandLabel.setText("Brand:");
        addDialog.getContentPane().add(addBrandLabel, new java.awt.GridBagConstraints());

        addBrandBox.setColumns(20);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        addDialog.getContentPane().add(addBrandBox, gridBagConstraints);

        addSerialLabel.setText("Serial #:");
        addDialog.getContentPane().add(addSerialLabel, new java.awt.GridBagConstraints());

        addSerialBox.setColumns(20);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        addDialog.getContentPane().add(addSerialBox, gridBagConstraints);

        addAcceptButton.setText("CREATE");
        addAcceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAcceptButtonActionPerformed(evt);
            }
        });
        addButtonPanel.add(addAcceptButton);

        addCancelButton.setText("CANCEL");
        addCancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCancelButtonActionPerformed(evt);
            }
        });
        addButtonPanel.add(addCancelButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        addDialog.getContentPane().add(addButtonPanel, gridBagConstraints);

        setLayout(new java.awt.BorderLayout());

        overlord.setAutoscrolls(true);
        overlord.setContinuousLayout(true);
        overlord.setName(""); // NOI18N

        leftsplitPanel.setMinimumSize(new java.awt.Dimension(350, 79));
        leftsplitPanel.setLayout(new java.awt.GridBagLayout());

        advSearchButton.setText("Search");
        advSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                advSearchButtonActionPerformed(evt);
            }
        });
        leftsplitFilterButtonPanel.add(advSearchButton);

        showallButton.setText("Show All");
        showallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showallButtonActionPerformed(evt);
            }
        });
        leftsplitFilterButtonPanel.add(showallButton);

        excelButton.setText("Export to Excel");
        excelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excelButtonActionPerformed(evt);
            }
        });
        leftsplitFilterButtonPanel.add(excelButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        leftsplitPanel.add(leftsplitFilterButtonPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        leftsplitPanel.add(jSeparator1, gridBagConstraints);

        sortLabel.setText("Sort By:");
        leftsplitSortButtonPanel.add(sortLabel);

        leftsplitSortButtonPanel.add(sortCombo);

        sortReverseCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ascending", "Descending" }));
        leftsplitSortButtonPanel.add(sortReverseCombo);

        sortButton.setText("Sort");
        sortButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortButtonActionPerformed(evt);
            }
        });
        leftsplitSortButtonPanel.add(sortButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        leftsplitPanel.add(leftsplitSortButtonPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        leftsplitPanel.add(leftsplitSortByPanel, gridBagConstraints);

        instruTable.setModel(instruments);
        instruTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        instruTable.getTableHeader().setReorderingAllowed(false);
        instruTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                instruTableMouseClicked(evt);
            }
        });
        leftsplitinstruTablePane.setViewportView(instruTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        leftsplitPanel.add(leftsplitinstruTablePane, gridBagConstraints);

        addButton.setText("ADD NEW INSTRUMENT");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        leftsplitaddButtonPanel.add(addButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        leftsplitPanel.add(leftsplitaddButtonPanel, gridBagConstraints);

        overlord.setLeftComponent(leftsplitPanel);

        rightsplitPanel.setMinimumSize(new java.awt.Dimension(450, 308));
        rightsplitPanel.setLayout(new java.awt.BorderLayout());

        infoTabs.setMinimumSize(new java.awt.Dimension(1086, 350));

        detailPanel.setMinimumSize(new java.awt.Dimension(1081, 200));
        detailPanel.setLayout(new java.awt.GridBagLayout());

        statusLabel.setText("Status:");
        statusLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        detailPanel.add(statusLabel, gridBagConstraints);

        statusCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "In Storage", "At Shop", "On Loan", "Missing" }));
        statusCombo.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        detailPanel.add(statusCombo, gridBagConstraints);

        instrumentLabel.setText("Instrument:");
        instrumentLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        detailPanel.add(instrumentLabel, gridBagConstraints);

        instruBox.setBackground(new java.awt.Color(240, 240, 240));
        instruBox.setColumns(20);
        instruBox.setEditable(false);
        instruBox.setAutoscrolls(false);
        instruBox.setMaximumSize(new java.awt.Dimension(1000, 20));
        instruBox.setMinimumSize(new java.awt.Dimension(1000, 20));
        instruBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instruBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        detailPanel.add(instruBox, gridBagConstraints);

        brandLabel.setText("Brand:");
        brandLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        detailPanel.add(brandLabel, gridBagConstraints);

        brandBox.setBackground(new java.awt.Color(240, 240, 240));
        brandBox.setColumns(20);
        brandBox.setEditable(false);
        brandBox.setAutoscrolls(false);
        brandBox.setMinimumSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        detailPanel.add(brandBox, gridBagConstraints);

        serialLabel.setText("Serial Number:");
        serialLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        detailPanel.add(serialLabel, gridBagConstraints);

        serialBox.setBackground(new java.awt.Color(240, 240, 240));
        serialBox.setColumns(20);
        serialBox.setEditable(false);
        serialBox.setAutoscrolls(false);
        serialBox.setMinimumSize(new java.awt.Dimension(200, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        detailPanel.add(serialBox, gridBagConstraints);

        rankLabel.setText("Rank:");
        rankLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        detailPanel.add(rankLabel, gridBagConstraints);

        rankBox.setColumns(2);
        rankBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rankBox.setText("3");
        rankBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rankBoxKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        detailPanel.add(rankBox, gridBagConstraints);

        valueLabel.setText("Value: $");
        valueLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        detailPanel.add(valueLabel, gridBagConstraints);

        valueBox.setColumns(20);
        valueBox.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        valueBox.setText("0");
        valueBox.setAutoscrolls(false);
        valueBox.setMinimumSize(new java.awt.Dimension(200, 20));
        valueBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                valueBoxKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        detailPanel.add(valueBox, gridBagConstraints);

        strapLabel.setText("Neck Strap:");
        strapLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        detailPanel.add(strapLabel, gridBagConstraints);

        strapCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Yes", "No", "n/a" }));
        strapCombo.setPreferredSize(null);
        strapCombo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                strapComboPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        detailPanel.add(strapCombo, gridBagConstraints);

        ligatureLabel.setText("Ligature:");
        ligatureLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        detailPanel.add(ligatureLabel, gridBagConstraints);

        ligCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Yes", "No", "n/a" }));
        ligCombo.setPreferredSize(null);
        ligCombo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ligComboPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        ligCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ligComboActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        detailPanel.add(ligCombo, gridBagConstraints);

        mpieceLabel.setText("Mouthpiece:");
        mpieceLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        detailPanel.add(mpieceLabel, gridBagConstraints);

        mpieceCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Yes", "No", "n/a" }));
        mpieceCombo.setPreferredSize(null);
        mpieceCombo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                mpieceComboPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        detailPanel.add(mpieceCombo, gridBagConstraints);

        capLabel.setText("Mouthpiece Cap:");
        capLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        detailPanel.add(capLabel, gridBagConstraints);

        capCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Yes", "No", "n/a" }));
        capCombo.setPreferredSize(null);
        capCombo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                capComboPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        detailPanel.add(capCombo, gridBagConstraints);

        bowLabel.setText("Bow:");
        bowLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        detailPanel.add(bowLabel, gridBagConstraints);

        bowCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Yes", "No", "n/a" }));
        bowCombo.setPreferredSize(null);
        bowCombo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                bowComboPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        detailPanel.add(bowCombo, gridBagConstraints);

        noteLabel.setText("Notes:");
        noteLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        detailPanel.add(noteLabel, gridBagConstraints);

        detailNotePanel.setMinimumSize(new java.awt.Dimension(25, 25));

        notesTPane.setMinimumSize(new java.awt.Dimension(25, 25));
        notesTPane.setPreferredSize(null);
        notesTPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                notesTPaneKeyTyped(evt);
            }
        });
        detailNotePanel.setViewportView(notesTPane);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        detailPanel.add(detailNotePanel, gridBagConstraints);

        infoTabs.addTab("Details", detailPanel);

        historyPanel.setLayout(new java.awt.BorderLayout());

        historySplit.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        historyTablePanel.setMinimumSize(new java.awt.Dimension(100, 200));

        historyTable.setModel(histModel);
        historyTable.getTableHeader().setReorderingAllowed(false);
        historyTablePanel.setViewportView(historyTable);

        historySplit.setTopComponent(historyTablePanel);

        checkoutPanel.setLayout(new java.awt.GridBagLayout());

        renterLabel.setText("Renter:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        checkoutPanel.add(renterLabel, gridBagConstraints);

        renterBox.setColumns(25);
        renterBox.setAutoscrolls(false);
        renterBox.setMinimumSize(new java.awt.Dimension(200, 20));
        renterBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renterBoxActionPerformed(evt);
            }
        });
        renterBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                renterBoxKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        checkoutPanel.add(renterBox, gridBagConstraints);

        schoolyearLabel.setText("School Year:");
        schoolyearLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        checkoutPanel.add(schoolyearLabel, gridBagConstraints);

        schoolyearBox.setColumns(25);
        schoolyearBox.setAutoscrolls(false);
        schoolyearBox.setMinimumSize(new java.awt.Dimension(200, 20));
        schoolyearBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                schoolyearBoxKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        checkoutPanel.add(schoolyearBox, gridBagConstraints);

        dateoutLabel.setText("Date Out:");
        dateoutLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        checkoutPanel.add(dateoutLabel, gridBagConstraints);

        dateoutBox.setColumns(25);
        dateoutBox.setAutoscrolls(false);
        dateoutBox.setMinimumSize(new java.awt.Dimension(200, 20));
        dateoutBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dateoutBoxKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        checkoutPanel.add(dateoutBox, gridBagConstraints);

        periodLabel.setText("For Use In:");
        periodLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        checkoutPanel.add(periodLabel, gridBagConstraints);

        periodCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7" }));
        periodCombo.setPreferredSize(null);
        periodCombo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                periodComboPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        periodCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                periodComboActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        checkoutPanel.add(periodCombo, gridBagConstraints);

        feeLabel.setText("Fee Paid:");
        feeLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        checkoutPanel.add(feeLabel, gridBagConstraints);

        feeCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Paid", "Unpaid", "Waived" }));
        feeCombo.setSelectedIndex(1);
        feeCombo.setPreferredSize(null);
        feeCombo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                feeComboPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        checkoutPanel.add(feeCombo, gridBagConstraints);

        contractLabel.setText("Contract:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        checkoutPanel.add(contractLabel, gridBagConstraints);

        contractCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Uncreated", "Created", "Signed" }));
        contractCombo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                contractComboPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        checkoutPanel.add(contractCombo, gridBagConstraints);

        otherLabel.setText("Other:");
        otherLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        checkoutPanel.add(otherLabel, gridBagConstraints);

        otherBox.setPreferredSize(null);
        otherBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                otherBoxKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        checkoutPanel.add(otherBox, gridBagConstraints);

        checkoutButtonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        formButton.setText("Generate Form");
        formButton.setPreferredSize(null);
        formButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formButtonActionPerformed(evt);
            }
        });
        checkoutButtonPanel.add(formButton);

        checkoutButton.setText("Check Out");
        checkoutButton.setPreferredSize(null);
        checkoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkoutButtonActionPerformed(evt);
            }
        });
        checkoutButtonPanel.add(checkoutButton);

        checkinButton.setText("Check In");
        checkinButton.setPreferredSize(null);
        checkinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkinButtonActionPerformed(evt);
            }
        });
        checkoutButtonPanel.add(checkinButton);

        lostButton.setText("Lost");
        lostButton.setPreferredSize(null);
        lostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lostButtonActionPerformed(evt);
            }
        });
        checkoutButtonPanel.add(lostButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        checkoutPanel.add(checkoutButtonPanel, gridBagConstraints);

        historySplit.setRightComponent(checkoutPanel);

        historyPanel.add(historySplit, java.awt.BorderLayout.CENTER);

        infoTabs.addTab("History", historyPanel);

        rightsplitPanel.add(infoTabs, java.awt.BorderLayout.CENTER);

        saveButton.setText("SAVE CHANGES");
        saveButton.setPreferredSize(null);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        rightsplitButtonPanel.add(saveButton);

        cancelButton.setText("CANCEL CHANGES");
        cancelButton.setPreferredSize(null);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        rightsplitButtonPanel.add(cancelButton);

        deleteButton.setText("DELETE INSTRUMENT");
        deleteButton.setPreferredSize(null);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        rightsplitButtonPanel.add(deleteButton);

        rightsplitPanel.add(rightsplitButtonPanel, java.awt.BorderLayout.SOUTH);

        overlord.setRightComponent(rightsplitPanel);

        add(overlord, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void instruBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_instruBoxActionPerformed
    {//GEN-HEADEREND:event_instruBoxActionPerformed
}//GEN-LAST:event_instruBoxActionPerformed

    private void ligComboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ligComboActionPerformed
    {//GEN-HEADEREND:event_ligComboActionPerformed
}//GEN-LAST:event_ligComboActionPerformed

    private void renterBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_renterBoxActionPerformed
    {//GEN-HEADEREND:event_renterBoxActionPerformed
}//GEN-LAST:event_renterBoxActionPerformed

    private void periodComboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_periodComboActionPerformed
    {//GEN-HEADEREND:event_periodComboActionPerformed
}//GEN-LAST:event_periodComboActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deleteButtonActionPerformed
    {//GEN-HEADEREND:event_deleteButtonActionPerformed
        Main.window.setEnabled(false);
        Instrument instru = getSelectedInstrument();

        if(!instru.isValid())
        {
            JOptionPane.showMessageDialog(jopDialog,
                    "Instrument could not be deleted: No instrument selected.",
                    "Delete Failed",
                    JOptionPane.WARNING_MESSAGE);
            Main.window.setEnabled(true);
            Main.window.requestFocus();
            return;
        }

        int n = JOptionPane.showConfirmDialog(jopDialog,
                "Are you sure you want to delete this instrument?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        switch(n)
        {
            case JOptionPane.YES_OPTION:
                int index = instruments.displayIndexOf(instru);
                instruments.delete(instru);
                sort();

                int rows = instruments.getRowCount();
                int selection = (index>=rows) ? rows-1 : index;
                setSelectedIndex(selection);
        }


        Main.window.setEnabled(true);
        Main.window.requestFocus();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addButtonActionPerformed
    {//GEN-HEADEREND:event_addButtonActionPerformed
        Main.window.setEnabled(false);

        addTypeBox.setText("");
        addBrandBox.setText("");
        addSerialBox.setText("");

        addDialog.setVisible(true);
    }//GEN-LAST:event_addButtonActionPerformed

    private void advSearchButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_advSearchButtonActionPerformed
    {//GEN-HEADEREND:event_advSearchButtonActionPerformed
        Main.window.setEnabled(false);
        advsearchDialog.setVisible(true);
    }//GEN-LAST:event_advSearchButtonActionPerformed

    private void advsearchResetButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_advsearchResetButtonActionPerformed
    {//GEN-HEADEREND:event_advsearchResetButtonActionPerformed
        advsearchPanel.removeAll();
        advsearchAddFieldButtonActionPerformed(evt);
        advsearchDialog.setMinimumSize(new Dimension(470, 150));
        advsearchDialog.setSize(0, 0);
}//GEN-LAST:event_advsearchResetButtonActionPerformed

    private void advsearchCancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_advsearchCancelButtonActionPerformed
    {//GEN-HEADEREND:event_advsearchCancelButtonActionPerformed
        advsearchDialog.setVisible(false);
        Main.window.setEnabled(true);
        Main.window.requestFocus();
    }//GEN-LAST:event_advsearchCancelButtonActionPerformed

    private void advsearchAddFieldButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_advsearchAddFieldButtonActionPerformed
    {//GEN-HEADEREND:event_advsearchAddFieldButtonActionPerformed
        advsearchPanel.remove(advsearchAddFieldButton);
        advsearchPanel.remove(advsearchButtonPanel);

        JComboBox cb = new JComboBox();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        cb.addItem("Contains");
        cb.addItem("Without");
        advsearchPanel.add(cb);
        cb = new JComboBox();
        for(String s : Instrument.attributes)
        {
            cb.addItem(s);
        }
        advsearchPanel.add(cb);
        JTextField txt = new JTextField();
        txt.setColumns(20);
        advsearchPanel.add(txt, gbc);

        advsearchPanel.add(advsearchAddFieldButton, gbc);
        advsearchPanel.add(advsearchButtonPanel, gbc);

        Dimension size = advsearchDialog.getSize();
        Dimension minSize = advsearchDialog.getMinimumSize();
        advsearchDialog.setSize(size.width, size.height+25);
        minSize.setSize(minSize.width, minSize.height+25);
        advsearchDialog.setMinimumSize(minSize);
    }//GEN-LAST:event_advsearchAddFieldButtonActionPerformed

    private void addCancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addCancelButtonActionPerformed
    {//GEN-HEADEREND:event_addCancelButtonActionPerformed
        addDialog.setVisible(false);
        Main.window.setEnabled(true);
        Main.window.requestFocus();
    }//GEN-LAST:event_addCancelButtonActionPerformed

    private void addAcceptButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addAcceptButtonActionPerformed
    {//GEN-HEADEREND:event_addAcceptButtonActionPerformed
        String name = addTypeBox.getText();
        String brand = addBrandBox.getText();
        String serial = addSerialBox.getText();
        
        // Check to make sure that all of the fields were properly filled in
        if(!Instrument.isValid(name, brand, serial))
        {
            JOptionPane.showMessageDialog(jopDialog,
                    "All of the three fields on the \"Add Instrument\" form \n"
                    + "are required to be filled in. One of them was left blank.",
                    "Data Entry Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check to make sure that there isn't already an instrument by the same
        // name as this one
        if(!instruments.isUnique(name, brand, serial))
        {
            JOptionPane.showMessageDialog(jopDialog,
                    "There is already an instrument in the database that has \n"
                    + "the same name, brand, and serial as this one.",
                    "Data Entry Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!Instrument.isSaveable(name, brand, serial))
        {
            JOptionPane.showMessageDialog(jopDialog,
                    "Currently, the only characters allowed for use in any of "
                    + "the \"Add Instrument\" window fields are A - Z (both "
                    + "upper case and lower case) 0 - 9, dash, and space.  The "
                    + "text that you entered in one of those fields contains a "
                    + "character that is not allowed.",
                    "Data Entry Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Creating a new instrument
        Instrument instru = new Instrument();

        try
        {
            // Adding core fields from the "Add Instrument" window
            instru.set("Instrument", addTypeBox.getText());
            instru.set("Brand", addBrandBox.getText());
            instru.set("Serial", addSerialBox.getText());

            // Adding default values for new instruments
            // TODO: Move these into some sort of configuration system
            instru.set("Rank", "3");
            instru.set("Value", "0");
            instru.set("Period", "0");
            instru.set("Fee", "Unpaid");
            instru.set("Contract", "Uncreated");
            instru.addHistory("Instrument created.");
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(jopDialog,
                    "An internal error has occurred while creating the "
                    +"instrument:\n"+ex.getMessage(),
                    "Instrument Creation Failed",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Add the instrument to the instrument list
        instruments.add(instru);
        sort();
        setSelectedInstrument(instru);

        addDialog.setVisible(false);
        Main.window.setEnabled(true);
        Main.window.requestFocus();
    }//GEN-LAST:event_addAcceptButtonActionPerformed

    private void sortButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_sortButtonActionPerformed
    {//GEN-HEADEREND:event_sortButtonActionPerformed
        Instrument selected = getSelectedInstrument();
        sort();
        setSelectedInstrument(selected);
    }//GEN-LAST:event_sortButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_saveButtonActionPerformed
    {//GEN-HEADEREND:event_saveButtonActionPerformed
        Instrument i = getSelectedInstrument();
        i.addHistory("Instrument saved.");
        saveDetails(getSelectedInstrument());
        saveHistory(getSelectedInstrument());
        displayInstrument();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void instruTableMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_instruTableMouseClicked
    {//GEN-HEADEREND:event_instruTableMouseClicked
}//GEN-LAST:event_instruTableMouseClicked

    private void lostButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_lostButtonActionPerformed
    {//GEN-HEADEREND:event_lostButtonActionPerformed
        Instrument i = getSelectedInstrument();
        i.addHistory("Instrument found missing.");
        statusCombo.setSelectedItem("Missing");
        saveHistory(getSelectedInstrument());
        displayInstrument();
    }//GEN-LAST:event_lostButtonActionPerformed

    private void checkinButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_checkinButtonActionPerformed
    {//GEN-HEADEREND:event_checkinButtonActionPerformed
        Instrument i = getSelectedInstrument();
        i.addHistory("Instrument checked in.");
        statusCombo.setSelectedItem("In Storage");
        saveHistory(getSelectedInstrument());
        displayInstrument();
}//GEN-LAST:event_checkinButtonActionPerformed

    private void checkoutButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_checkoutButtonActionPerformed
    {//GEN-HEADEREND:event_checkoutButtonActionPerformed
        Instrument i = getSelectedInstrument();
        i.addHistory("Instrument checked out.");
        statusCombo.setSelectedItem("On Loan");
        saveHistory(getSelectedInstrument());
        displayInstrument();
}//GEN-LAST:event_checkoutButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButtonActionPerformed
    {//GEN-HEADEREND:event_cancelButtonActionPerformed
        displayInstrument();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void formButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_formButtonActionPerformed
    {//GEN-HEADEREND:event_formButtonActionPerformed

        Instrument i = getSelectedInstrument();
        saveHistory(getSelectedInstrument());
        i.addHistory("Contract Generated for "+i.get("Renter")+".");
        conGen.generateContract(i);
        displayInstrument();
    }//GEN-LAST:event_formButtonActionPerformed

    private void advsearchSearchButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_advsearchSearchButtonActionPerformed
    {//GEN-HEADEREND:event_advsearchSearchButtonActionPerformed
        Component[] comps = advsearchPanel.getComponents();
        int stop = comps.length/3;
        InstrumentAttributeMatcher[] matchers = new InstrumentAttributeMatcher[stop];
        for(int i = 0; i<stop; i++)
        {
            matchers[i] = jcomps2matcher((JComboBox) comps[i*3], (JComboBox) comps[i*3+1], (JTextField) comps[i*3+2]);
        }

        Instrument selection = getSelectedInstrument();
        instruments.selectList(matchers);
        setSelectedInstrument(selection);

        advsearchDialog.setVisible(false);
        Main.window.setEnabled(true);
        Main.window.requestFocus();
    }//GEN-LAST:event_advsearchSearchButtonActionPerformed

    private void showallButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_showallButtonActionPerformed
    {//GEN-HEADEREND:event_showallButtonActionPerformed
        Instrument selected = getSelectedInstrument();
        instruments.selectList(InstrumentList.SHOWALL);
        sort();
        setSelectedInstrument(selected);
    }//GEN-LAST:event_showallButtonActionPerformed

    private void excelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excelButtonActionPerformed
        instruments.exportToExcel();
    }//GEN-LAST:event_excelButtonActionPerformed

    private void rankBoxKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_rankBoxKeyTyped
    {//GEN-HEADEREND:event_rankBoxKeyTyped
        detailChange = true;
    }//GEN-LAST:event_rankBoxKeyTyped

    private void valueBoxKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_valueBoxKeyTyped
    {//GEN-HEADEREND:event_valueBoxKeyTyped
        detailChange = true;
    }//GEN-LAST:event_valueBoxKeyTyped

    private void strapComboPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_strapComboPopupMenuWillBecomeInvisible
    {//GEN-HEADEREND:event_strapComboPopupMenuWillBecomeInvisible
        detailChange = true;
    }//GEN-LAST:event_strapComboPopupMenuWillBecomeInvisible

    private void ligComboPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_ligComboPopupMenuWillBecomeInvisible
    {//GEN-HEADEREND:event_ligComboPopupMenuWillBecomeInvisible
        detailChange = true;
    }//GEN-LAST:event_ligComboPopupMenuWillBecomeInvisible

    private void mpieceComboPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_mpieceComboPopupMenuWillBecomeInvisible
    {//GEN-HEADEREND:event_mpieceComboPopupMenuWillBecomeInvisible
        detailChange = true;
    }//GEN-LAST:event_mpieceComboPopupMenuWillBecomeInvisible

    private void capComboPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_capComboPopupMenuWillBecomeInvisible
    {//GEN-HEADEREND:event_capComboPopupMenuWillBecomeInvisible
        detailChange = true;
    }//GEN-LAST:event_capComboPopupMenuWillBecomeInvisible

    private void bowComboPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_bowComboPopupMenuWillBecomeInvisible
    {//GEN-HEADEREND:event_bowComboPopupMenuWillBecomeInvisible
        detailChange = true;
    }//GEN-LAST:event_bowComboPopupMenuWillBecomeInvisible

    private void notesTPaneKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_notesTPaneKeyTyped
    {//GEN-HEADEREND:event_notesTPaneKeyTyped
        detailChange = true;
    }//GEN-LAST:event_notesTPaneKeyTyped

    private void renterBoxKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_renterBoxKeyTyped
    {//GEN-HEADEREND:event_renterBoxKeyTyped
        histChange = true;
    }//GEN-LAST:event_renterBoxKeyTyped

    private void schoolyearBoxKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_schoolyearBoxKeyTyped
    {//GEN-HEADEREND:event_schoolyearBoxKeyTyped
        histChange = true;
    }//GEN-LAST:event_schoolyearBoxKeyTyped

    private void dateoutBoxKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_dateoutBoxKeyTyped
    {//GEN-HEADEREND:event_dateoutBoxKeyTyped
        histChange = true;
    }//GEN-LAST:event_dateoutBoxKeyTyped

    private void periodComboPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_periodComboPopupMenuWillBecomeInvisible
    {//GEN-HEADEREND:event_periodComboPopupMenuWillBecomeInvisible
        histChange = true;
    }//GEN-LAST:event_periodComboPopupMenuWillBecomeInvisible

    private void feeComboPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_feeComboPopupMenuWillBecomeInvisible
    {//GEN-HEADEREND:event_feeComboPopupMenuWillBecomeInvisible
        histChange = true;
    }//GEN-LAST:event_feeComboPopupMenuWillBecomeInvisible

    private void contractComboPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_contractComboPopupMenuWillBecomeInvisible
    {//GEN-HEADEREND:event_contractComboPopupMenuWillBecomeInvisible
        histChange = true;
    }//GEN-LAST:event_contractComboPopupMenuWillBecomeInvisible

    private void otherBoxKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_otherBoxKeyTyped
    {//GEN-HEADEREND:event_otherBoxKeyTyped
        histChange = true;
    }//GEN-LAST:event_otherBoxKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAcceptButton;
    private javax.swing.JTextField addBrandBox;
    private javax.swing.JLabel addBrandLabel;
    private javax.swing.JButton addButton;
    private javax.swing.JPanel addButtonPanel;
    private javax.swing.JButton addCancelButton;
    private javax.swing.JDialog addDialog;
    private javax.swing.JTextField addSerialBox;
    private javax.swing.JLabel addSerialLabel;
    private javax.swing.JLabel addTextLabel;
    private javax.swing.JTextField addTypeBox;
    private javax.swing.JLabel addTypeLabel;
    private javax.swing.JButton advSearchButton;
    private javax.swing.JButton advsearchAddFieldButton;
    private javax.swing.JPanel advsearchButtonPanel;
    private javax.swing.JButton advsearchCancelButton;
    private javax.swing.JDialog advsearchDialog;
    private javax.swing.JPanel advsearchPanel;
    private javax.swing.JButton advsearchResetButton;
    private javax.swing.JButton advsearchSearchButton;
    private javax.swing.JComboBox bowCombo;
    private javax.swing.JLabel bowLabel;
    private javax.swing.JTextField brandBox;
    private javax.swing.JLabel brandLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox capCombo;
    private javax.swing.JLabel capLabel;
    private javax.swing.JButton checkinButton;
    private javax.swing.JButton checkoutButton;
    private javax.swing.JPanel checkoutButtonPanel;
    private javax.swing.JPanel checkoutPanel;
    private javax.swing.JComboBox contractCombo;
    private javax.swing.JLabel contractLabel;
    private javax.swing.JTextField dateoutBox;
    private javax.swing.JLabel dateoutLabel;
    private javax.swing.JButton deleteButton;
    private javax.swing.JScrollPane detailNotePanel;
    private javax.swing.JPanel detailPanel;
    private javax.swing.JButton excelButton;
    private javax.swing.JComboBox feeCombo;
    private javax.swing.JLabel feeLabel;
    private javax.swing.JButton formButton;
    private javax.swing.JPanel historyPanel;
    private javax.swing.JSplitPane historySplit;
    private javax.swing.JTable historyTable;
    private javax.swing.JScrollPane historyTablePanel;
    private javax.swing.JTabbedPane infoTabs;
    private javax.swing.JTextField instruBox;
    private javax.swing.JTable instruTable;
    private javax.swing.JLabel instrumentLabel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JDialog jopDialog;
    private javax.swing.JPanel leftsplitFilterButtonPanel;
    private javax.swing.JPanel leftsplitPanel;
    private javax.swing.JPanel leftsplitSortButtonPanel;
    private javax.swing.JPanel leftsplitSortByPanel;
    private javax.swing.JPanel leftsplitaddButtonPanel;
    private javax.swing.JScrollPane leftsplitinstruTablePane;
    private javax.swing.JComboBox ligCombo;
    private javax.swing.JLabel ligatureLabel;
    private javax.swing.JButton lostButton;
    private javax.swing.JComboBox mpieceCombo;
    private javax.swing.JLabel mpieceLabel;
    private javax.swing.JLabel noteLabel;
    private javax.swing.JTextPane notesTPane;
    private javax.swing.JTextField otherBox;
    private javax.swing.JLabel otherLabel;
    private javax.swing.JSplitPane overlord;
    private javax.swing.JComboBox periodCombo;
    private javax.swing.JLabel periodLabel;
    private javax.swing.JTextField rankBox;
    private javax.swing.JLabel rankLabel;
    private javax.swing.JTextField renterBox;
    private javax.swing.JLabel renterLabel;
    private javax.swing.JPanel rightsplitButtonPanel;
    private javax.swing.JPanel rightsplitPanel;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField schoolyearBox;
    private javax.swing.JLabel schoolyearLabel;
    private javax.swing.JTextField serialBox;
    private javax.swing.JLabel serialLabel;
    private javax.swing.JButton showallButton;
    private javax.swing.JButton sortButton;
    private javax.swing.JComboBox sortCombo;
    private javax.swing.JLabel sortLabel;
    private javax.swing.JComboBox sortReverseCombo;
    private javax.swing.JComboBox statusCombo;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JComboBox strapCombo;
    private javax.swing.JLabel strapLabel;
    private javax.swing.JTextField valueBox;
    private javax.swing.JLabel valueLabel;
    // End of variables declaration//GEN-END:variables
}
