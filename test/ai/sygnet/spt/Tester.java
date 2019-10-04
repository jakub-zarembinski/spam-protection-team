package ai.sygnet.spt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class Tester {

	private static final String ENDPOINT = "http://localhost:8080/spam-protection-team";
	private static final String REPORT_ID = "1070bcf3-26f6-488d-be9b-81042f509bb5";
	private static final String NON_EXISTENT_REPORT_ID = "00000000000000000000000";

	public static void main(String[] args) {
		
		final Client client = ClientBuilder.newClient(new ClientConfig());
		final WebTarget service = client.target(UriBuilder.fromUri(ENDPOINT).build());
		
		{
			/*
			 * Set all reports to OPEN 
			 */
			ReportResource.TicketState payload = new ReportResource.TicketState();
			payload.ticketState = ReportResource.TicketEnum.OPEN.name();
			Entity<ReportResource.TicketState> entity = Entity.entity(payload, MediaType.APPLICATION_XML);
			Response response = service.path("/reports").request(MediaType.APPLICATION_XML).put(entity, Response.class);
			System.out.println(response.getStatus());
			assertEquals(200, response.getStatus());
		}
		
		{
			/*
			 * Test report list size and make sure all are set to OPEN
			 */
			Response response = service.path("/reports").request(MediaType.APPLICATION_XML).get(Response.class);
			System.out.println(response.getStatus());
			assertEquals(200, response.getStatus());
			ReportResource.Reports reports = response.readEntity(ReportResource.Reports.class);
			System.out.println(reports.elements.size());
			assertEquals(25, reports.elements.size());
			for (Report report : reports.elements)
				assertEquals(ReportResource.TicketEnum.OPEN.name(), report.state);
		}
		
		{
			/*
			 * Make sure the selected report exists and is set to OPEN
			 */
			Response response = service.path("/reports/:" + REPORT_ID).request(MediaType.APPLICATION_XML).get(Response.class);
			System.out.println(response.getStatus());
			assertEquals(200, response.getStatus());
			Report report = response.readEntity(Report.class);
			System.out.println(report.id);
			assertEquals(REPORT_ID, report.id);
			System.out.println(report.state);
			assertEquals(ReportResource.TicketEnum.OPEN.name(), report.state);
		}

		{
			/*
			 * Set the selected report to BLOCKED
			 */
			ReportResource.TicketState payload = new ReportResource.TicketState();
			payload.ticketState = ReportResource.TicketEnum.BLOCKED.name();
			Entity<ReportResource.TicketState> entity = Entity.entity(payload, MediaType.APPLICATION_XML);
			Response response = service.path("/reports/:" + REPORT_ID).request(MediaType.APPLICATION_XML).put(entity, Response.class);
			System.out.println(response.getStatus());
			assertEquals(200, response.getStatus());
			Report report = response.readEntity(Report.class);
			System.out.println(report.state);
			assertEquals(ReportResource.TicketEnum.BLOCKED.name(), report.state);
		}
		
		{	
			/*
			 * Make sure the selected report is set to BLOCKED
			 */
			Response response = service.path("/reports/:" + REPORT_ID).request(MediaType.APPLICATION_XML).get(Response.class);
			System.out.println(response.getStatus());
			assertEquals(200, response.getStatus());
			Report report = response.readEntity(Report.class);
			System.out.println(report.id);
			assertEquals(REPORT_ID, report.id);
			System.out.println(report.state);
			assertEquals(ReportResource.TicketEnum.BLOCKED.name(), report.state);
		}

		{
			/*
			 * Set the selected report to RESOLVED
			 */
			ReportResource.TicketState payload = new ReportResource.TicketState();
			payload.ticketState = ReportResource.TicketEnum.RESOLVED.name();
			Entity<ReportResource.TicketState> entity = Entity.entity(payload, MediaType.APPLICATION_XML);
			Response response = service.path("/reports/:" + REPORT_ID).request(MediaType.APPLICATION_XML).put(entity, Response.class);
			System.out.println(response.getStatus());
			assertEquals(200, response.getStatus());
			Report report = response.readEntity(Report.class);
			System.out.println(report.state);
			assertEquals(ReportResource.TicketEnum.RESOLVED.name(), report.state);
		}
		
		{	
			/*
			 * Make sure the selected report is set to RESOLVED
			 */
			Response response = service.path("/reports/:" + REPORT_ID).request(MediaType.APPLICATION_XML).get(Response.class);
			System.out.println(response.getStatus());
			assertEquals(200, response.getStatus());
			Report report = response.readEntity(Report.class);
			System.out.println(report.id);
			assertEquals(REPORT_ID, report.id);
			System.out.println(report.state);
			assertEquals(ReportResource.TicketEnum.RESOLVED.name(), report.state);
		}
		
		{
			/*
			 * Test report list size after one of them has been resolved
			 */
			Response response = service.path("/reports").queryParam("state", ReportResource.TicketEnum.OPEN.name())
					.request(MediaType.APPLICATION_XML).get(Response.class);
			System.out.println(response.getStatus());
			assertEquals(200, response.getStatus());		
			ReportResource.Reports reports = response.readEntity(ReportResource.Reports.class);
			System.out.println(reports.elements.size());
			assertEquals(24, reports.elements.size());
		}
		
		{
			/*
			 * Test report list size after one of them has been resolved
			 */
			Response response = service.path("/reports").queryParam("state", ReportResource.TicketEnum.RESOLVED.name())
					.request(MediaType.APPLICATION_XML).get(Response.class);
			System.out.println(response.getStatus());
			assertEquals(200, response.getStatus());		
			ReportResource.Reports reports = response.readEntity(ReportResource.Reports.class);
			System.out.println(reports.elements.size());
			assertEquals(1, reports.elements.size());
		}
		
		{
			/*
			 * Make sure an attempt to fetch a non-existent report fails with the correct error
			 */
			Response response = service.path("/reports/:" + NON_EXISTENT_REPORT_ID).request(MediaType.APPLICATION_XML).get(Response.class);
			System.out.println(response.getStatus());
			assertEquals(404, response.getStatus());
		}
		
		{
			/*
			 * Make sure an attempt to update a non-existent report fails with the correct error
			 */
			ReportResource.TicketState payload = new ReportResource.TicketState();
			payload.ticketState = ReportResource.TicketEnum.OPEN.name();
			Entity<ReportResource.TicketState> entity = Entity.entity(payload, MediaType.APPLICATION_XML);
			Response response = service.path("/reports/:" + NON_EXISTENT_REPORT_ID).request(MediaType.APPLICATION_XML).put(entity, Response.class);
			System.out.println(response.getStatus());
			assertEquals(400, response.getStatus());
		}
	}
}