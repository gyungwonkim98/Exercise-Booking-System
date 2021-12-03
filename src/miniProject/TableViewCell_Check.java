package miniProject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

class TableViewCell_Check extends TableCell<CheckDataModel,Button> {

    private Button cellButton;

    TableViewCell_Check(){
         cellButton = new Button("����");
         cellButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent t) {
                System.out.println("��ư�� ���Ⱦ��");
                System.out.println("Selected item: " + getIndex());
                int selectitem = getIndex();
                
                BookingController.class_nowcount_minus(selectitem); 
                BookingController.member_count_plus() ;
                BookingController.check_delete(selectitem) ;
                BookingController.alert("���� ���", "�����Ͻ� ������ ��ҵǾ����ϴ�.");
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
