package com.jobportal.utility;

public class Data {
    public static String getMessageBody(String otp, String name) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Your OTP Code</title>\n" +
                "    <style>\n" +
                "        body {\n" + 
                "		 	font-family: Arial, sans-serif; \n" +
                "        	margin:0;\n" +
                "		 	padding: 0;\n"+
                "		 	background-color: #f4f4f4;\n" +
                "		 }\n"+
                "		 .container{\n" +
                "		 	max-width: 600px;\n" +
                "		 	margin: 0 auto;\n" +
                "		 	padding: 20px;\n" +
                "		 	background-color: 60 4px 8px rgba(0, 0, 0, 0.1);\n" +
                "		 }\n"+
                "		 .header{\n" +
                "		 	padding: 20px;\n" +
                "		 	color: #333333;\n" +
                "		 	text-align: center;\n" +
                "		 }\n"+
                "		 .otp{\n" +
                "		 	font-size: 24px; \n" +
                "		 	font-weight: bold; \n" +
                "		 	color: #4CAF50;\n" +
                "		 	margin: 20px 0;\n" +
                "		 }\n"+
                "		 .footer{\n" +
                "		 	margin-top: 20px;\n" +
                "		 	font-size: 12px; \n" +
                "		 	color: #888888;\n" +
                "		 	text-align: center;\n" +
                "		 }\n"+
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "    	<div class=\"header\">\n" +
                "			<h1>Your OTP Code</h1>\n" +
                "    	</div>\n" +
                "    	<div class=\"body\">\n" +
                "			<p>Hello, "+name+"</p>\n" +
                "			<p>We have received a request to verify your email address. To complete this process, please use the One-Time Password (OTP) below:</p>\n" +
                "			<div class=\"otp\">" + otp + "</div>\n" +
                "			<p>This OTP code is valid for 5 minutes. If you did not make this request, please disregard this email.</p>\n" +
                "			<p>Thank you for using our service!</p>\n" +
                "		</div>\n" +
                "		<div class=\"footer\">\n" +
                "			<p>&copy; 2025 iJOBS. All rights reserved.</p>\n" +
                "		</div>\n" +
                "	</div>\n" +
                "</body>\n" +
                "</html>";
    }
}
