package miniProject;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

class ListViewCell_Book extends ListCell<ClassDataModel>{
	
	@FXML
    private AnchorPane bookCellPane;
    @FXML
    private Label booktimelb = new Label();
    @FXML
    private Label bookcontentlb = new Label();
    @FXML
    private Label bookteacherlb = new Label();
    @FXML
    private Label booknowcountlb = new Label();
    @FXML
    private Label booktotalcountlb = new Label();
    @FXML
    private Label booknumlb = new Label();
   
    @FXML
	public Button bookbtn;
    
    @FXML
    public void bookAddClick(ActionEvent event) {
    	 System.out.println("Selected item: " + getIndex());
    	 	
    	 		
    	 	BookingController.nowmembermodel_reset();
            
			int classnum = Integer.parseInt(booknumlb.getText());
			int nowcount = Integer.parseInt(booknowcountlb.getText());
			int totalcount = Integer.parseInt(booktotalcountlb.getText());
			
			//--1 현재 예약가능한지(정원확인)
			while(nowcount>=totalcount) {
				BookingController.alert("예약 불가", "해당 수업은 현재 예약정원 초과로 예약이 불가합니다.");
				break;
			}
			
			while(nowcount<totalcount) {
				System.out.println("nowcount = " +nowcount+"\t totalcount = " +totalcount);
				//--2 현재 회원의 횟수가 0이 아닌지
				if(BookingController.nowMemberModel.getMember_count() != 0) {
					System.out.println("현재 회원 횟수 : " +BookingController.nowMemberModel.getMember_count());
					//--3 같은날에 수업 예약 있는지 확인하는 메서드 
					System.out.println("같은날짜에 예약된 수업 없음 : (false여야 가능-->)" + BookingController.book_date_check());
					if(!BookingController.book_date_check()) {
						System.out.println("같은날짜에 예약된 수업 없음 : (false여야 가능-->)" + BookingController.book_date_check());
						BookingController.class_nowcount_plus(classnum);
						BookingController.member_count_minus();
						System.out.println("모든 조건 충족/ 수업 추가만 하면 성공");
						
						//--4 booktb에 수업 추가
						BookingController.book_insert(classnum);
						BookingController.class_select_date(BookingController.bookselectDate);
					
						System.out.println("수업 추가 성공");
						BookingController.alert("예약 완료", "해당 수업 예약이 완료되었습니다.");
						break;
					}else {
						BookingController.alert("예약 불가", "같은날 예약된 수업이 있습니다.");
						// 팝업 - 같은날에 이미 예약된 수업이 있습니다.
						break;
					}
				}else {
					BookingController.alert("예약 불가", "회원님의 예약 가능한 횟수가 없습니다.");
					break;
				}
			}
    }
			 
    
    
    public ListViewCell_Book(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listCellItem_Book.fxml"));
        fxmlLoader.setController(this);
        try{	fxmlLoader.load();	}
        catch (IOException e) {  throw new RuntimeException(e); }
    }

    
    public void updateItem(ClassDataModel classDM, boolean empty) {
        super.updateItem(classDM,empty);
        if(classDM != null && !empty){
        	bookCellPane.setVisible(true);
        	booktimelb.setText(classDM.getClass_time());
        	bookcontentlb.setText(classDM.getClass_content());
        	bookteacherlb.setText(classDM.getClass_teacher());
        	booknowcountlb.setText(String.valueOf(classDM.getClass_nowcount()));
        	booktotalcountlb.setText(String.valueOf(classDM.getClass_totalcount()));
        	booknumlb.setText(String.valueOf(classDM.getClass_num()));
            setGraphic(bookCellPane);
        }else {
        	bookCellPane.setVisible(false);
        }
        
    }
}