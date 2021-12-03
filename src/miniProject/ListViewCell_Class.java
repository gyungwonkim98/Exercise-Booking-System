package miniProject;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;


class ListViewCell_Class extends ListCell<ClassDataModel>{
	
	@FXML
    private AnchorPane cellPane;
    @FXML
    private Label classtimelb = new Label();
    @FXML
    private Label classcontentlb = new Label();
    @FXML
    private Label classteacherlb = new Label();
    @FXML
    private Label classnowcountlb = new Label();
    @FXML
    private Label classtotalcountlb = new Label();
    @FXML
    private Label classnumlb = new Label();
   
    @FXML
	public Button classdelbtn;
    
    @FXML
    public void classDelClick(ActionEvent event) {
    	 System.out.println("Selected item: " + getIndex());
         int classnum = Integer.parseInt(classnumlb.getText());
         BookingController.classList.remove(getIndex());

         //������ ������ ����� ȸ���� 
         //member_count ++1
         BookingController.member_count_plus_2(classnum);
         System.out.println("������ ������ ����� ȸ��Ƚ�� ����");
         //class table���� ���� ����
         //-- book�� ���� ������ - cascade �ɾ��
         BookingController.class_delete(classnum);
         BookingController.alert("���� ���� �Ϸ�", "�����Ͻ� ������ �����Ǿ����ϴ�.");
         System.out.println("���� ���� �Ϸ�");
    }

	public ListViewCell_Class(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listCellItem_Class.fxml"));
        fxmlLoader.setController(this);
        try{ fxmlLoader.load();	}
        catch (IOException e) { throw new RuntimeException(e); }
    }

    
    public void updateItem(ClassDataModel classDM, boolean empty) {
        super.updateItem(classDM,empty);
        if(classDM != null && !empty){
        	cellPane.setVisible(true);
        	classtimelb.setText(classDM.getClass_time());
        	classcontentlb.setText(classDM.getClass_content());
        	classteacherlb.setText(classDM.getClass_teacher());
        	classnowcountlb.setText(String.valueOf(classDM.getClass_nowcount()));
        	classtotalcountlb.setText(String.valueOf(classDM.getClass_totalcount()));
        	classnumlb.setText(String.valueOf(classDM.getClass_num()));
            setGraphic(cellPane);
        }else {
        	cellPane.setVisible(false);
        }
        
    }
}