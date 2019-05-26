import com.sun.org.apache.xpath.internal.operations.Equals;
import com.sun.rowset.internal.Row;
import localization.LocalizationManager;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.RowFilter.ComparisonType;

import static javax.xml.datatype.DatatypeConstants.EQUAL;

public class MyTable extends JTable {


    public static LocalizationManager localizationManager = MainClient.localizationManager;

    // TODO: Получать наименования столбцов таблицы метаданными
    // todo: update теперь все в getColumnName

    @Getter
    private static TardisModel tardisModel = new TardisModel();
    @Getter
    public static TableRowSorter<TableModel> sorter;

    @Override
    public int convertRowIndexToView(int modelRowIndex) {
        return super.convertRowIndexToView(modelRowIndex);
    }

    public MyTable(MainFrame frame) {

        this.setModel(tardisModel);

        for (String str : MainClient.datamass) {
            tardisModel.addTimeTr(MainClient.getGson().fromJson(str, TimeTraveller.class));
        }


        tardisModel.updateTableAndGraphics(MainClient.datamass.stream()
                .map(s -> MainClient.getGson().fromJson(s, TimeTraveller.class))
                .map(s ->
                        new Circle(
                                s.getX(),
                                s.getY(),
                                s.getAge() + 40,
                                s.getAge() + 40,
                                new Color(
                                        ((s.getOwner().hashCode() >> 24 & 255) ^ (s.getOwner().hashCode() >> 16 & 255) ^ (s.getOwner().hashCode() >> 8  & 255) ^ (s.getOwner().hashCode() & 255)) % 256,
                                        ((s.getOwner().hashCode() >> 16 & 255) ^ (s.getOwner().hashCode() >> 8  & 255) ^ (s.getOwner().hashCode() & 255)) % 256,
                                        ((s.getOwner().hashCode() >> 8  & 255) ^ (s.getOwner().hashCode() & 255)) % 256),
                                MyTable.getTardisModel().getData().indexOf(s)))
                .collect(Collectors.toList()));

        sorter = new TableRowSorter<>(tardisModel);
        this.setRowSorter(sorter);

        this.getSelectionModel().addListSelectionListener(e -> {
            int rowIndex = this.getSelectedRow();
            if (rowIndex == -1) return;
            if (tardisModel.isCellEditable(sorter.convertRowIndexToModel(rowIndex))) {
                this.setSelectionBackground(new Color(123, 104, 238));
            } else {
                this.setSelectionBackground(Color.GRAY);
            }
            frame.setTraveller(tardisModel.getTimeTravelerAtRow(rowIndex), tardisModel.isCellEditable(sorter.convertRowIndexToModel(rowIndex)));


        });
    }


    static class TardisModel extends AbstractTableModel {

        final int columnCount = 6;
        @Getter
        private ArrayList<TimeTraveller> data;
        @Getter
        private ArrayList<Circle> circles;

        public TardisModel() {
            data = new ArrayList<>();
            circles = new ArrayList<>();
        }

        public void updateTableAndGraphics(List<Circle> newCircles) {
            circles.clear();
            circles.addAll(newCircles);

            MainFrame.downRightPanel.removeAll();
            MainFrame.downRightPanel.revalidate();
            MainFrame.downRightPanel.repaint();
        }

        private void drawA() {
            for (TimeTraveller tr : data) {
                new Thread(() -> {
                    Color myColor = new Color(
                            ((tr.getOwner().hashCode() >> 24 & 255) ^ (tr.getOwner().hashCode() >> 16 & 255) ^ (tr.getOwner().hashCode() >> 8  & 255) ^ (tr.getOwner().hashCode() & 255)) % 256,
                            ((tr.getOwner().hashCode() >> 16 & 255) ^ (tr.getOwner().hashCode() >> 8  & 255) ^ (tr.getOwner().hashCode() & 255)) % 256,
                            ((tr.getOwner().hashCode() >> 8  & 255) ^ (tr.getOwner().hashCode() & 255)) % 256);

                    Circle c = new Circle(tr.getX(), tr.getY(), 0, 0, new Color((myColor.getRed() | 128) % 256, (myColor.getGreen() | 64) % 256, (myColor.getBlue() | 192) % 256), MyTable.getTardisModel().getData().indexOf(tr));
//                    c.setColor(new Color((tr.getOwner().hashCode() & 242), tr.getOwner().hashCode() & 37, tr.getOwner().hashCode() & 255).brighter().brighter().brighter());
                    circles.add(c);
                    long ageN = tr.getAge() + 40;
                    long ageCurr = 0;
                    while (ageCurr < ageN) {
                        ageCurr += 5;
                        c.setWidth(ageCurr);
                        c.setHeight(ageCurr);
                        MainFrame.jSplitPane1.repaint();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }).start();
            }
        }

        public void replaceTime(TimeTraveller newT) {
            for (TimeTraveller treveller : data) {
                if (treveller.equals(newT)) {
                    data.remove(treveller);
                    data.add(newT);
                    break;
                }
            }

            circles.clear();
//            circles.addAll(data.stream().map(s -> new Circle(s.getX(), s.getY(), 0, s.getAge() + 10, MyTable.getTardisModel().getData().indexOf(s))).collect(Collectors.toList()));
            this.fireTableDataChanged();

            drawA();

        }

        public void addTimeTr(TimeTraveller tt) {
            data.add(tt);
            circles.clear();
            this.fireTableDataChanged();
            drawA();
        }

        public void removeTr(TimeTraveller tr){
            data.remove(tr);
            circles.clear();
            this.fireTableDataChanged();
            drawA();

        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public boolean isCellEditable(int rowIndex) {
            String own = MainClient.getCookie().split("\\|")[0];
            return data.get(rowIndex).getOwner().equalsIgnoreCase(own);
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return localizationManager.getNativeTitle(LocalizationManager.TITLE_NAME);
                case 1:
                    return localizationManager.getNativeTitle(LocalizationManager.TITLE_DATEOFBIRTHDAY);
                case 2:
                    return localizationManager.getNativeTitle(LocalizationManager.TITLE_CURDATE);
                case 3:
                    return localizationManager.getNativeTitle(LocalizationManager.TITLE_X);
                case 4:
                    return localizationManager.getNativeTitle(LocalizationManager.TITLE_Y);
                case 5:
                    return localizationManager.getNativeTitle(LocalizationManager.TITLE_AGE);
            }
            return "";
        }

        @Override
        public Class<?> getColumnClass(int column) {
            Class returnValue;
            if ((column == 3) || (column == 4)) {
                returnValue = Double.class;
            } else if (column == 5) {
                returnValue = Integer.class;
            } else if ((column >= 0) && (column < getColumnCount())) {
                returnValue = getValueAt(0, column).getClass();
            } else {
                returnValue = Object.class;
            }
            return returnValue;
        }

        @Override
        public int getColumnCount() {
            return columnCount;
        }

        public TimeTraveller getTimeTravelerAtRow(int row) {
            return data.get(sorter.convertRowIndexToModel(row));
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            TimeTraveller t = data.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return t.getName();
                case 1:
                    return t.getBirthdayDate().DatetoString();
                case 2:
                    return t.getLocDate().DatetoString();
                case 3:
                    return t.getX();
                case 4:
                    return t.getY();
                case 5:
                    return t.getAge();
                default:
                    return null;
            }
        }
    }
}