package ufc.quixada.npi.gpa.model;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class DownloadDocumento extends HttpEntity<byte[]>{

	private HttpEntity<byte[]> httpEntity;
	
	public DownloadDocumento(byte[] arquivo, String nomeArquivo){
		HttpHeaders httpHeaders = new HttpHeaders();
		
		httpHeaders.add("Content-disposition", "attachment; filename=\""+nomeArquivo+"\"");
		httpEntity = new HttpEntity<byte[]>(arquivo, httpHeaders);
	}
	
	public HttpHeaders getHeaders() {
        return httpEntity.getHeaders();
    }
 
    public byte[] getBody() {
        return httpEntity.getBody();
    }
 
    public boolean hasBody() {
        return httpEntity.hasBody();
    }
 
    public boolean equals(Object other) {
        return httpEntity.equals(other);
    }
 
    public int hashCode() {
        return httpEntity.hashCode();
    }
 
    public String toString() {
        return httpEntity.toString();
    }
}
