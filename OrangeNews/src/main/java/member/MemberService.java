package member;

public class MemberService {
	MemberDAO memberDAO;
	
	public MemberService() {
		memberDAO=new MemberDAO();
	}
	
	public boolean join(MemberVO member) {
		return memberDAO.insertMember(member);
	}
	
	public boolean login(MemberVO member) {
		boolean loginValue=memberDAO.selectMember(member, 1);
		return loginValue;
	}
	
	public void getMemberInfo(MemberVO member) {
		memberDAO.selectMember(member, 0);
	}
	
	public void editInfo(MemberVO member) {
		memberDAO.updateMember(member);
	}
	
	public boolean findId(MemberVO member) {
		boolean isId=memberDAO.selectId(member);
		return isId;
	}
	
	public boolean findPwd(MemberVO member) {
		boolean isPwd=memberDAO.selectPwd(member);
		return isPwd;
	}
	
	public void withdraw(String id) {
		memberDAO.deleteMember(id);
	}
}
