package interface_adapter.room.create;

import interface_adapter.ViewModel;

public class CreateRoomViewModel extends ViewModel<CreateRoomState> {

    public CreateRoomViewModel() {
        super("create_room");
        setState(new CreateRoomState());
    }
}
