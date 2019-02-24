package es.grapecheck.plataforma.webaction.seguridad;

import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omnifaces.filter.HttpFilter;

import es.grapecheck.plataforma.persistencia.vo.UsuarioVO;
import es.grapecheck.plataforma.utiles.Constantes;

/**
 * @author rsanchez
 *
 */

public class FiltroSeguridad extends HttpFilter {
	
	String stExcludePatterns;
	private Pattern excludeUrls;
	private String[] arrExcludeUrls;
	
	public void init(FilterConfig config) throws ServletException {
		this.stExcludePatterns = config.getInitParameter("excludePatterns");		
		// this.excludeUrls = Pattern.compile(stExcludePatterns, Pattern.CASE_INSENSITIVE);
		arrExcludeUrls = stExcludePatterns.split(":");
	}
	   
	/**
	 * Realiza el filtro de seguridad de la aplicacion 
	 * @param request
	 * @param response
	 * @param chain
	 * @throws java.io.IOException
	 * @throws ServletException
	 */
	public void doFilter(HttpServletRequest request, HttpServletResponse response,HttpSession session, FilterChain chain) throws java.io.IOException, ServletException {
		
		String stServletPath = request.getServletPath();
		System.out.println("stServletPath:" + stServletPath);
		
		
		// Matcher matcher = excludeUrls.matcher(stServletPath);

		// System.out.println("stServletPath:" + stServletPath + "matcher.matches:" + matcher.matches());
		
		// if ( !matcher.matches() ) {
		
		Boolean blExcluir = Boolean.FALSE;
		
		for ( int i = 0; i < arrExcludeUrls.length; i++ ) {
			if ( stServletPath.contains(arrExcludeUrls[i]) ) {
				blExcluir = Boolean.TRUE;
			}
		}
		
		if ( !blExcluir ) {
			UsuarioVO usuario = (UsuarioVO) request.getSession().getAttribute(Constantes.USUARIO_LOGIN);
			
			if ( usuario == null ) { 
				System.err.println("Acceso Denegado.Usuario no logeado:" + stServletPath);
				response.sendRedirect("login.xhtml");
			}
		}
		
		chain.doFilter(request,response);
	}
	 
	public void destroy() {
		/* Called before the Filter instance is removed from service by the web container*/
	}
}
