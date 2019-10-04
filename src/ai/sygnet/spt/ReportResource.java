package ai.sygnet.spt;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

@Path("/reports")
public class ReportResource {

	/**
	 * Fetch a list of all unresolved reports
	 * @return Response with a list of unresolved reports
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getReports(@QueryParam("state") List<String> state) {
		Reports reports = new Reports();
		if (state.isEmpty()) {
			reports.elements = ReportDAO.instance.getModel().values().stream().collect(Collectors.toList());
		} else {
			reports.elements = ReportDAO.instance.getModel().values().stream()
					.filter(report -> state.contains(report.state))
					.collect(Collectors.toList());
		}
		return Response.ok().entity(reports).build();
	}

	/**
	 * Fetch a selected report
	 * @param id The ID of the report
	 * @return Response with the selected report or an error if it's not found
	 */
	@GET
	@Path("/:{reportId}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getReport(@PathParam("reportId") String id) {
		Report report = ReportDAO.instance.getModel().get(id);
		if (report == null)
			return Response.status(404).entity("Report not found").build();
		return Response.ok().entity(report).build();
	}
	
	/**
	 * Update the state of all reports
	 * @param payload The new state to be applied
	 * @return Response with a confirmation
	 */
	@PUT
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response updateReports(TicketState payload) {
		for (Report report : ReportDAO.instance.getModel().values())
			report.state = payload.ticketState;
		return Response.ok().entity("done").build();
	}

	/**
	 * Update the state of a selected report
	 * If the state is already set to BLOCKED, set it to OPEN, otherwise apply the new state 
	 * @param id The ID of the report
	 * @param payload The new state to be applied
	 * @return Response with the updated report or an error if it's not found
	 */
	@PUT
	@Path("/:{reportId}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response updateReport(@PathParam("reportId") String id, TicketState payload) {
		Report report = ReportDAO.instance.getModel().get(id);
		if (report == null)
			return Response.status(400).entity("Cannot update a non-existing report").build();
		if (payload.ticketState.equals(report.state))
			report.state = TicketEnum.OPEN.name();
		else
			report.state = payload.ticketState;
		return Response.ok().entity(report).build();
	}
	
	@XmlRootElement
	public static class Reports {
		public List<Report> elements; 
	}

	@XmlRootElement
	public static class TicketState {
		public String ticketState;
	}

	public static enum TicketEnum {
		OPEN, BLOCKED, RESOLVED;
	}

}