package ai.sygnet.spt;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Report {

	public String id;
	public String source;
	public String sourceIdentityId;
	public Reference reference;
	public String state;
	public Payload payload;
	public String created;

	public static class Payload {
		public String source;
		public String reportType;
		public Object message;
		public String reportId;
		public String referenceResourceId;
		public String referenceResourceType;
	}

	public static class Reference {
		public String referenceId;
		public String referenceType;
	}
}
