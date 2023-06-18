package reply;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class replyVO {

	private int articleNum;
	private String userID;
	private String contents;
	private int replyNum;
	private int retype; //상위인지 하위인지
	private int good;
	private int bad;
	private String date;
	
}
