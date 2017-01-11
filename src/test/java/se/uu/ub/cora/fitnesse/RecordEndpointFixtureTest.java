package se.uu.ub.cora.fitnesse;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RecordEndpointFixtureTest {
	private RecordEndpointFixture fixture;
	private HttpHandlerFactorySpy httpHandlerFactorySpy;

	@BeforeMethod
	public void setUp() {
		SystemUrl.url = "http://localhost:8080/therest/";
		DependencyProvider
				.setHttpHandlerFactoryClassName("se.uu.ub.cora.fitnesse.HttpHandlerFactorySpy");
		httpHandlerFactorySpy = (HttpHandlerFactorySpy) DependencyProvider.getFactory();
		fixture = new RecordEndpointFixture();
	}

	@Test
	public void testReadRecordDataForFactoryIsOk() {
		fixture.setType("someType");
		fixture.setId("someId");
		fixture.setAuthToken("someToken");
		fixture.testReadRecord();
		assertEquals(httpHandlerFactorySpy.httpHandlerSpy.requestMetod, "GET");
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId?authToken=someToken");
	}

	@Test
	public void testReadRecordOk() {
		assertEquals(fixture.testReadRecord(), "Everything ok");
	}

	@Test
	public void testReadRecordNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testReadRecord(), "bad things happend");
	}

	@Test
	public void testReadIncomingLinksDataForFactoryIsOk() {
		fixture.setType("someType");
		fixture.setId("someId");
		fixture.setAuthToken("someToken");
		fixture.testReadIncomingLinks();
		assertEquals(httpHandlerFactorySpy.httpHandlerSpy.requestMetod, "GET");
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId/incomingLinks?authToken=someToken");
	}

	@Test
	public void testReadIncomingLinksOk() {
		assertEquals(fixture.testReadIncomingLinks(), "Everything ok");
	}

	@Test
	public void testReadIncomingLinksNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testReadIncomingLinks(), "bad things happend");
	}

	@Test
	public void testReadRecordListDataForFactoryIsOk() {
		fixture.setType("someType");
		fixture.setAuthToken("someToken");
		fixture.testReadRecordList();
		assertEquals(httpHandlerFactorySpy.httpHandlerSpy.requestMetod, "GET");
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType?authToken=someToken");
	}

	@Test
	public void testReadRecordListOk() {
		assertEquals(fixture.testReadRecordList(), "Everything ok");
	}

	@Test
	public void testReadRecordListNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testReadRecordList(), "bad things happend");
	}

	@Test
	public void testCreateRecordDataForFactoryIsOk() {
		httpHandlerFactorySpy.setResponseCode(201);
		fixture.setType("someType");
		fixture.setAuthToken("someToken");
		fixture.setJson("{\"name\":\"value\"}");
		fixture.testCreateRecord();
		HttpHandlerSpy httpHandlerSpy = httpHandlerFactorySpy.httpHandlerSpy;
		assertEquals(httpHandlerSpy.requestMetod, "POST");
		assertEquals(httpHandlerSpy.outputString, "{\"name\":\"value\"}");
		assertEquals(httpHandlerSpy.requestProperties.get("Accept"), "application/uub+record+json");
		assertEquals(httpHandlerSpy.requestProperties.get("Content-Type"),
				"application/uub+record+json");
		assertEquals(httpHandlerSpy.requestProperties.size(), 2);
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType?authToken=someToken");
	}

	@Test
	public void testCreateRecordOk() {
		httpHandlerFactorySpy.setResponseCode(201);
		assertEquals(fixture.testCreateRecord(), "Everything ok");
	}

	@Test
	public void testCreateRecordNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testCreateRecord(), "bad things happend");
	}

	@Test
	public void testUpdateRecordDataForFactoryIsOk() {
		fixture.setType("someType");
		fixture.setId("someId");
		fixture.setAuthToken("someToken");
		fixture.setJson("{\"name\":\"value\"}");
		fixture.testUpdateRecord();
		HttpHandlerSpy httpHandlerSpy = httpHandlerFactorySpy.httpHandlerSpy;
		assertEquals(httpHandlerSpy.requestMetod, "POST");
		assertEquals(httpHandlerSpy.outputString, "{\"name\":\"value\"}");
		assertEquals(httpHandlerSpy.requestProperties.get("Accept"), "application/uub+record+json");
		assertEquals(httpHandlerSpy.requestProperties.get("Content-Type"),
				"application/uub+record+json");
		assertEquals(httpHandlerSpy.requestProperties.size(), 2);
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId?authToken=someToken");
	}

	@Test
	public void testUpdateRecordOk() {
		assertEquals(fixture.testUpdateRecord(), "Everything ok");
	}

	@Test
	public void testUpdateRecordNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testUpdateRecord(), "bad things happend");
	}

	@Test
	public void testDeleteRecordDataForFactoryIsOk() {
		fixture.setType("someType");
		fixture.setId("someId");
		fixture.setAuthToken("someToken");
		fixture.testDeleteRecord();
		HttpHandlerSpy httpHandlerSpy = httpHandlerFactorySpy.httpHandlerSpy;
		assertEquals(httpHandlerSpy.requestMetod, "DELETE");
		assertEquals(httpHandlerSpy.requestProperties.size(), 0);
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId?authToken=someToken");
	}

	@Test
	public void testDeleteRecordOk() {
		assertEquals(fixture.testDeleteRecord(), "Everything ok");
	}

	@Test
	public void testDeleteRecordNotOk() {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testDeleteRecord(), "bad things happend");
	}

	@Test
	public void testUploadDataForFactoryIsOk() throws ClientProtocolException, IOException {
		fixture.setType("someType");
		fixture.setId("someId");
		fixture.setAuthToken("someToken");
		fixture.setFileName("someFileName");
		fixture.testUpload();

		HttpMultiPartUploaderSpy httpHandlerSpy = httpHandlerFactorySpy.httpMultiPartUploaderSpy;
		assertEquals(httpHandlerSpy.headerFields.get("Accept"), "application/uub+record+json");
		assertEquals(httpHandlerSpy.headerFields.size(), 1);

		assertEquals(httpHandlerSpy.fieldName, "file");
		assertEquals(httpHandlerSpy.fileName, "someFileName");

		assertTrue(httpHandlerSpy.doneIsCalled);
		assertEquals(httpHandlerFactorySpy.urlString,
				"http://localhost:8080/therest/rest/record/someType/someId/master?authToken=someToken");
	}

	@Test
	public void testUploadOk() throws ClientProtocolException, IOException {
		assertEquals(fixture.testUpload(), "Everything ok");
	}

	@Test
	public void testUploadNotOk() throws ClientProtocolException, IOException {
		httpHandlerFactorySpy.changeFactoryToFactorInvalidHttpHandlers();
		assertEquals(fixture.testUpload(), "bad things happend");
	}
}
