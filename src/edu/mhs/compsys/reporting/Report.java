package edu.mhs.compsys.reporting;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;

import edu.mhs.compsys.idt.Change;

public class Report {

	private ArrayList<Integer> state1;
	private ArrayList<Integer> state2;
	private ArrayList<Change> changes;

	public Report() {
		state1 = new ArrayList<>();
		state2 = new ArrayList<>();
		changes = new ArrayList<>();

	}

	public void addChange(int s1, int s2, Change ch) {
		state1.add(s1);
		state2.add(s2);
		changes.add(ch);
	}

	/**
	 * @return A raw text report of the state changes in the format:
	 *         <code>[s1 s2][type=[class]:description @ bounds=[x=x, y=y, l=l, w=w]]</code>
	 */
	public String reportRaw() {
		String ret = "";
		for (int i = 0; i < state1.size(); i++) {
			ret += String.format("[%s %s] %s \n", state1.get(i), state2.get(i),
					changes.get(i));
		}
		return ret;
	}

	/**
	 * @return A pretty-printed html version of the changes as a report.
	 */
	public String reportHTML() {
		String template_start = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n"
				+ "    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
				+ " \n"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
				+ "<head>\n"
				+ "    <style media=\"screen\" type=\"text/css\">\n"
				+ "    html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, font, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td {\n"
				+ "    border: 0;\n"
				+ "    outline: 0;\n"
				+ "    font-weight: inherit;\n"
				+ "    font-style: inherit;\n"
				+ "    font-size: 100%;\n"
				+ "    font-family: inherit;\n"
				+ "    vertical-align: baseline;\n"
				+ "    margin: 0;\n"
				+ "    padding: 0;\n"
				+ "    }\n"
				+ " \n"
				+ "    body {\n"
				+ "    background: #FFF;\n"
				+ "    background-color: #333;\n"
				+ "    color: #333;\n"
				+ "    font: 12px sans-serif;\n"
				+ "    line-height: 22px;\n"
				+ "    }\n"
				+ " \n"
				+ "    .report {\n"
				+ "    width: 780px;\n"
				+ "    background-color: white;\n"
				+ "    border-bottom: 15px solid #000;\n"
				+ "    border-top: 15px solid #000;\n"
				+ "    border-left: 1px solid #0F0F0F;\n"
				+ "    border-right: 1px solid #0F0F0F;\n"
				+ "    margin: 66px auto 0;\n"
				+ "    padding: 0 0 40px;\n"
				+ "    }\n"
				+ " \n"
				+ "    .fixed {\n"
				+ "    display: block;\n"
				+ "    min-height: 1%;\n"
				+ "    }\n"
				+ " \n"
				+ "    .fixed:after {\n"
				+ "    content: \".\";\n"
				+ "    display: block;\n"
				+ "    height: 0;\n"
				+ "    clear: both;\n"
				+ "    visibility: hidden;\n"
				+ "    }\n"
				+ " \n"
				+ "    .header {\n"
				+ "    background-color: #F8F8F8;\n"
				+ "    margin: 0;\n"
				+ "    padding: 30px 40px;\n"
				+ "    font-size: 24px;\n"
				+ "    font-family: Arial, Helvetica, sans-serif;\n"
				+ "    font-weight: 800;\n"
				+ "    }\n"
				+ " \n"
				+ "    .footer {\n"
				+ "    background-color: #F8F8F8;\n"
				+ "    padding: 30px 40px;\n"
				+ "    font-size: 12px;\n"
				+ "    font-family: Arial, Helvetica, sans-serif;\n"
				+ "    }\n"
				+ " \n"
				+ " \n"
				+ "    .sc {\n"
				+ "	border: 1px solid #D0D0D0;\n"
				+ "	margin: 10px 10px 0 40px;\n"
				+ "	padding: 0 0 20px 0;\n"
				+ "	background-color: #F0F0F0;\n"
				+ "	} \n"
				+ "	.sc-name {\n"
				+ "	float: left;\n"
				+ "	padding: 10px 10px 10px 40px;\n"
				+ "	width: 679px;\n"
				+ "	background-color: #BBB;\n"
				+ "	font-size: 18px;\n"
				+ "	font-family: Arial, Helvetica, sans-serif;\n"
				+ "	margin-bottom: 10px;\n"
				+ "	}\n"
				+ " \n"
				+ "    .sc-details {\n"
				+ "    float: right;\n"
				+ "    margin: 10px;\n"
				+ "    width: 620px;\n"
				+ "    padding: 20px;\n"
				+ "    background-color: #BBB;\n"
				+ "    font-size: 12px;\n"
				+ "    font-family: Arial, Helvetica, sans-serif;\n"
				+ "    }\n"
				+ " \n"
				+ "    .application {\n"
				+ "    background-color: #EFFFB9;\n"
				+ "    border: 1px solid #99C600;\n"
				+ "    }\n"
				+ " \n"
				+ "    .window {\n"
				+ "    background-color: #FFEAC8;\n"
				+ "    border: 1px solid #D38E4A;\n"
				+ "    }\n"
				+ " \n"
				+ "    .taskbar {\n"
				+ "    background-color: #D0E4F4;\n"
				+ "    border: 1px solid #4D8FCB;\n"
				+ "    }\n"
				+ " \n"
				+ "    .desktop {\n"
				+ "    backgro	und-color: #F2CAFF;\n"
				+ "    border: 1px solid #D34ACE;\n"
				+ "    }\n"
				+ "    </style>\n"
				+ "  \n"
				+ "    <title>IDT 2013 - MHS</title>\n"
				+ "</head>\n"
				+ " \n"
				+ "<body>\n"
				+ "    <div class=\"report\">\n"
				+ "        <div class=\"header fixed\">\n"
				+ "            IDT 2013\n" + "        </div>";

		String ret = "";

		for (int i = 0; i < state1.size(); i++) {
			ret += String.format(
					"<div class=\"sc-name fixed %s\" %s->%s : %s </div>\n",
					changes.get(i).getType().getType(), state1.get(i),
					state2.get(i), changes.get(i));
		}

		String template_end = "       <div class=\"footer fixed\">\r\n"
				+ "<!-- Start MetaData Block -->\r\n" + (new Date()).toString()
				+ "<!-- End MetaData Block -->\r\n" + "        </div>\r\n"
				+ "    </div>\r\n" + "</body>\r\n" + "</html>";
		return template_start + ret + template_end;
	}

	public BufferedImage[] reportImages() {
		return null;
	}
}
