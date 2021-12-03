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

         //삭제전 수업이 예약된 회원의 
         //member_count ++1
         BookingController.member_count_plus_2(classnum);
         System.out.println("삭제전 수업이 예약된 회원횟수 증가");
         //class table에서 최종 삭제
         //-- book도 같이 삭제됨 - cascade 걸어놈
         BookingController.class_delete(classnum);
         BookingController.alert("수업 삭제 완료", "선택하신 수업이 삭제되었습니다.");
         System.out.println("수업 삭제 완료");
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