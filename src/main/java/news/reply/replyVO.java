package weather;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class replyVO {

	private int articleNum;
	private String userID;
	private String contents;
	private int replyNum;
	private int parentNum; 
	private int good;
	private int bad;
	private String date;
	
}
