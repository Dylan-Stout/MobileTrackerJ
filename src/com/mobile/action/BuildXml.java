package com.mobile.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import com.mobile.model.LocationData;
import com.mobile.model.MapData;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;

/**
 * Servlet implementation class BuildXml
 */
@WebServlet("/gps/BuildXml")
public class BuildXml extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuildXml() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get HttpSession and pull out map data
		HttpSession session = request.getSession(); 
		MapData map = (MapData) session.getAttribute("map"); 
		final Kml kml = KmlFactory.createKml(); 
		List<Feature> featureList = new ArrayList<Feature>(); 
		
	
		
		
		//Use location data in gps coordinates to create Placemarks in a KML object
		List<LocationData> ldata = map.getGpsCoords(); 
		for(int i = 0; i<ldata.size(); i++){ 
			LocationData entry = ldata.get(i); 
			Placemark placemark = KmlFactory.createPlacemark(); 
			placemark.setName("Marker #" + i);
			placemark.setVisibility(true);
			placemark.setOpen(false);
			placemark.setDescription(" Time: " + entry.getHrDate() +" Lat: " + entry.getLatitude() + " Long: " + entry.getLongitude());
			Point point = KmlFactory.createPoint(); 
			point.setExtrude(false);
			point.setAltitudeMode(AltitudeMode.CLAMP_TO_GROUND);
			point.getCoordinates().add(new Coordinate(entry.getLongitude()+","+entry.getLatitude()));
			placemark.setGeometry(point);
			featureList.add(placemark);
			
		}		
		kml.createAndSetDocument().setFeature(featureList);
		
		
		//Marshal with proper namespace (not ns2 default) 
		Marshaller marshaller = null; 
		OutputStream outStream = response.getOutputStream();
		try {
			marshaller = JAXBContext.newInstance(new Class[]{Kml.class}).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper()
			{
			    @Override
			    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix)
			    {
			        return namespaceUri.matches("http://www.w3.org/\\d{4}/Atom") ? "atom"
			                : (
			                namespaceUri.matches("urn:oasis:names:tc:ciq:xsdschema:xAL:.*?") ? "xal"
			                        : (
			                        namespaceUri.matches("http://www.google.com/kml/ext/.*?") ? "gx"
			                                : (
			                                namespaceUri.matches("http://www.opengis.net/kml/.*?") ? ""
			                                        : (
			                                        null
			                                        )
			                                )
			                        )
			                );
			    }
			});
			//get user agent of client
			String agent = request.getHeader("USER-AGENT"); 
			String filename = ""; 
			if(agent != null && agent.indexOf("MSIE")!= -1){ 
				filename = URLEncoder.encode(ldata.get(0).hrDate.substring(0, 9).replace(" ", "_") + "_mapExport.xml","UTF-8");
				response.setContentType("application/x-download"); 
				response.setHeader("Content-Disposition","attachment;filename=" + filename);
			}else if(agent != null && agent.indexOf("Mozilla") != -1){ 
				response.setCharacterEncoding("UTF-8");
	            response.setContentType("application/force-download");
	            response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			}
			//Set response type for xml 
//			response.setContentType("text/xml");
//			response.setCharacterEncoding("UTF-8");
//			response.setHeader("Content-Disposition", "attachment;filename=" + ldata.get(0).hrDate.substring(0, 9).replace(" ", "_") + "_mapExport.xml");
			marshaller.marshal(kml, outStream);
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
