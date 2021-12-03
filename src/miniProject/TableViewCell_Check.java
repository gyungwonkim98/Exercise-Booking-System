package miniProject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

class TableViewCell_Check extends TableCell<CheckDataModel,Button> {

    private Button cellButton;

    TableViewCell_Check(){
         cellButton = new Button("삭제");
         cellButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t) {
                System.out.println("버튼이 눌렸어요");
                System.out.println("Selected item: " + getIndex());
                int selectitem = getIndex();
                
                BookingController.class_nowcount_minus(selectitem); 
                BookingController.member_count_plus() ;
                BookingController.check_delete(selectitem) ;
                BookingController.alert("예약 취소", "예약하신 수업이 취소되었습니다.");
                BookingController.check_select(); 
					/*
					 * String checktotal = String.valueOf(BookingController.checkList.size());
					 * BookingController.checktotallbStatic.setText(checktotal);
					 */
            }
        });
    }

    @Override
    protected void updateItem(Button button, boolean empty) {
        super.updateItem(button, empty);
        if(!empty){
            setGraphic(cellButton);
        } else {
            setGraphic(null);
        }
    }
}
