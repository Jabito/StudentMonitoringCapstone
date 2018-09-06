package com.capstone.jmt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * Created by Jabito on 10/06/2017.
 */
@Service("emailService")
public class EmailService {

    private static Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void testSend(String email) {
        System.out.println("Sending email : " + email);
        logger.info("testSend");
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(email);
            helper.setReplyTo("noreply.aquajmt@gmail.com");
            helper.setFrom("noreply.aquajmt@gmail.com");
            helper.setSubject("AQUAJMT Registration Successful");
            helper.setText(emailTest.replace("XXXXXXXXXXX", "aasdjklnasldkajsd"), true);
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            logger.info("testSend", e);
            e.printStackTrace();
        } finally {
            logger.info("finally");
        }
    }

    public void sendPasswordReset(String email) {
        logger.info("sendPasswordReset", email);
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(email);
            helper.setReplyTo("noreply.aquajmt@gmail.com");
            helper.setFrom("noreply.aquajmt@gmail.com");
            helper.setSubject("Password Reset Email");
            logger.info("sendPasswordReset", mail);
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            logger.info("sendPasswordReset", e);
            e.printStackTrace();
        } finally {
            logger.info("finally");
        }
    }


    public void sendInPatientNotification(String email,
                                          String userEmail,
                                          String memberCode,
                                          String memberName,
                                          String hospitalName,
                                          String doctorName,
                                          String diagnosis,
                                          String roomNumber,
                                          String roomPrice,
                                          String category,
                                          String procedureDesc,
                                          Date dateAdmitted) {
        logger.info("sendInPatientNotification");
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(email);
            helper.setReplyTo("noreply@medicardphils.com");
            helper.setFrom("noreply@medicardphils.com");
            helper.setSubject("MACE Inpatient Notification: " + userEmail + " - " + memberName);
            helper.setText(inpatientNotification
                            .replace("XXXXXXXXXXX", memberCode)
                            .replace("YYYYYYuserEmail", userEmail)
                            .replace("YYYYYYmemberName", memberName)
                            .replace("YYYYYYhospitalName", hospitalName)
                            .replace("YYYYYYdoctorName", doctorName)
                            .replace("YYYYYYdiagnosis", diagnosis)
                            .replace("YYYYYYroomNumber", roomNumber)
                            .replace("YYYYYYroomPrice", roomPrice)
                            .replace("YYYYYYcategory", category)
                            .replace("YYYYYYprocedureDesc", procedureDesc)
                            .replace("YYYYYYdateAdmitted", dateAdmitted.toString().substring(0, 10) + dateAdmitted.toString().substring(dateAdmitted.toString().length() - 5, dateAdmitted.toString().length()))
                    , true);
            logger.info("sendPasswordReset", mail);
            System.out.println(mail);
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            logger.info("sendPasswordReset", e);
            e.printStackTrace();
        } finally {
            logger.info("finally");
        }
    }


    private String emailTest = "<!doctype html>\n" +
            "<html>\n" +
            "  <head>\n" +
            "    <meta name=\"viewport\" content=\"width=device-width\" />\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
            "    <title>Simple Transactional Email</title>\n" +
            "    <style>\n" +
            "      /* -------------------------------------\n" +
            "          GLOBAL RESETS\n" +
            "      ------------------------------------- */\n" +
            "      img {\n" +
            "        border: none;\n" +
            "        -ms-interpolation-mode: bicubic;\n" +
            "        max-width: 100%; }\n" +
            "\n" +
            "      body {\n" +
            "        background-color: #f6f6f6;\n" +
            "        font-family: sans-serif;\n" +
            "        -webkit-font-smoothing: antialiased;\n" +
            "        font-size: 14px;\n" +
            "        line-height: 1.4;\n" +
            "        margin: 0;\n" +
            "        padding: 0; \n" +
            "        -ms-text-size-adjust: 100%;\n" +
            "        -webkit-text-size-adjust: 100%; }\n" +
            "\n" +
            "      table {\n" +
            "        border-collapse: separate;\n" +
            "        mso-table-lspace: 0pt;\n" +
            "        mso-table-rspace: 0pt;\n" +
            "        width: 100%; }\n" +
            "        table td {\n" +
            "          font-family: sans-serif;\n" +
            "          font-size: 14px;\n" +
            "          vertical-align: top; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          BODY & CONTAINER\n" +
            "      ------------------------------------- */\n" +
            "\n" +
            "      .body {\n" +
            "        background-color: #f6f6f6;\n" +
            "        width: 100%; }\n" +
            "\n" +
            "      /* Set a max-width, and make it display as block so it will automatically stretch to that width, but will also shrink down on a phone or something */\n" +
            "      .container {\n" +
            "        display: block;\n" +
            "        Margin: 0 auto !important;\n" +
            "        /* makes it centered */\n" +
            "        max-width: 580px;\n" +
            "        padding: 10px;\n" +
            "        width: auto !important;\n" +
            "        width: 580px; }\n" +
            "\n" +
            "      /* This should also be a block element, so that it will fill 100% of the .container */\n" +
            "      .content {\n" +
            "        box-sizing: border-box;\n" +
            "        display: block;\n" +
            "        Margin: 0 auto;\n" +
            "        max-width: 580px;\n" +
            "        padding: 10px; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          HEADER, FOOTER, MAIN\n" +
            "      ------------------------------------- */\n" +
            "      .main {\n" +
            "        background: #fff;\n" +
            "        border-radius: 3px;\n" +
            "        width: 100%; }\n" +
            "\n" +
            "      .wrapper {\n" +
            "        box-sizing: border-box;\n" +
            "        padding: 20px; }\n" +
            "\n" +
            "      .footer {\n" +
            "        clear: both;\n" +
            "        padding-top: 10px;\n" +
            "        text-align: center;\n" +
            "        width: 100%; }\n" +
            "        .footer td,\n" +
            "        .footer p,\n" +
            "        .footer span,\n" +
            "        .footer a {\n" +
            "          color: #999999;\n" +
            "          font-size: 12px;\n" +
            "          text-align: center; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          TYPOGRAPHY\n" +
            "      ------------------------------------- */\n" +
            "      h1,\n" +
            "      h2,\n" +
            "      h3,\n" +
            "      h4 {\n" +
            "        color: #000000;\n" +
            "        font-family: sans-serif;\n" +
            "        font-weight: 400;\n" +
            "        line-height: 1.4;\n" +
            "        margin: 0;\n" +
            "        Margin-bottom: 30px; }\n" +
            "\n" +
            "      h1 {\n" +
            "        font-size: 35px;\n" +
            "        font-weight: 300;\n" +
            "        text-align: center;\n" +
            "        text-transform: capitalize; }\n" +
            "\n" +
            "      p,\n" +
            "      ul,\n" +
            "      ol {\n" +
            "        font-family: sans-serif;\n" +
            "        font-size: 14px;\n" +
            "        font-weight: normal;\n" +
            "        margin: 0;\n" +
            "        Margin-bottom: 15px; }\n" +
            "        p li,\n" +
            "        ul li,\n" +
            "        ol li {\n" +
            "          list-style-position: inside;\n" +
            "          margin-left: 5px; }\n" +
            "\n" +
            "      a {\n" +
            "        color: #3498db;\n" +
            "        text-decoration: underline; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          BUTTONS\n" +
            "      ------------------------------------- */\n" +
            "      .btn {\n" +
            "        box-sizing: border-box;\n" +
            "        width: 100%; }\n" +
            "        .btn > tbody > tr > td {\n" +
            "          padding-bottom: 15px; }\n" +
            "        .btn table {\n" +
            "          width: auto; }\n" +
            "        .btn table td {\n" +
            "          background-color: #ffffff;\n" +
            "          border-radius: 5px;\n" +
            "          text-align: center; }\n" +
            "        .btn a {\n" +
            "          background-color: #ffffff;\n" +
            "          border: solid 1px #3498db;\n" +
            "          border-radius: 5px;\n" +
            "          box-sizing: border-box;\n" +
            "          color: #3498db;\n" +
            "          cursor: pointer;\n" +
            "          display: inline-block;\n" +
            "          font-size: 14px;\n" +
            "          font-weight: bold;\n" +
            "          margin: 0;\n" +
            "          padding: 12px 25px;\n" +
            "          text-decoration: none;\n" +
            "          text-transform: capitalize; }\n" +
            "\n" +
            "      .btn-primary table td {\n" +
            "        background-color: #3498db; }\n" +
            "\n" +
            "      .btn-primary a {\n" +
            "        background-color: #3498db;\n" +
            "        border-color: #3498db;\n" +
            "        color: #ffffff; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          OTHER STYLES THAT MIGHT BE USEFUL\n" +
            "      ------------------------------------- */\n" +
            "      .last {\n" +
            "        margin-bottom: 0; }\n" +
            "\n" +
            "      .first {\n" +
            "        margin-top: 0; }\n" +
            "\n" +
            "      .align-center {\n" +
            "        text-align: center; }\n" +
            "\n" +
            "      .align-right {\n" +
            "        text-align: right; }\n" +
            "\n" +
            "      .align-left {\n" +
            "        text-align: left; }\n" +
            "\n" +
            "      .clear {\n" +
            "        clear: both; }\n" +
            "\n" +
            "      .mt0 {\n" +
            "        margin-top: 0; }\n" +
            "\n" +
            "      .mb0 {\n" +
            "        margin-bottom: 0; }\n" +
            "\n" +
            "      .preheader {\n" +
            "        color: transparent;\n" +
            "        display: none;\n" +
            "        height: 0;\n" +
            "        max-height: 0;\n" +
            "        max-width: 0;\n" +
            "        opacity: 0;\n" +
            "        overflow: hidden;\n" +
            "        mso-hide: all;\n" +
            "        visibility: hidden;\n" +
            "        width: 0; }\n" +
            "\n" +
            "      .powered-by a {\n" +
            "        text-decoration: none; }\n" +
            "\n" +
            "      hr {\n" +
            "        border: 0;\n" +
            "        border-bottom: 1px solid #f6f6f6;\n" +
            "        Margin: 20px 0; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
            "      ------------------------------------- */\n" +
            "      @media only screen and (max-width: 620px) {\n" +
            "        table[class=body] h1 {\n" +
            "          font-size: 28px !important;\n" +
            "          margin-bottom: 10px !important; }\n" +
            "        table[class=body] p,\n" +
            "        table[class=body] ul,\n" +
            "        table[class=body] ol,\n" +
            "        table[class=body] td,\n" +
            "        table[class=body] span,\n" +
            "        table[class=body] a {\n" +
            "          font-size: 16px !important; }\n" +
            "        table[class=body] .wrapper,\n" +
            "        table[class=body] .article {\n" +
            "          padding: 10px !important; }\n" +
            "        table[class=body] .content {\n" +
            "          padding: 0 !important; }\n" +
            "        table[class=body] .container {\n" +
            "          padding: 0 !important;\n" +
            "          width: 100% !important; }\n" +
            "        table[class=body] .main {\n" +
            "          border-left-width: 0 !important;\n" +
            "          border-radius: 0 !important;\n" +
            "          border-right-width: 0 !important; }\n" +
            "        table[class=body] .btn table {\n" +
            "          width: 100% !important; }\n" +
            "        table[class=body] .btn a {\n" +
            "          width: 100% !important; }\n" +
            "        table[class=body] .img-responsive {\n" +
            "          height: auto !important;\n" +
            "          max-width: 100% !important;\n" +
            "          width: auto !important; }}\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          PRESERVE THESE STYLES IN THE HEAD\n" +
            "      ------------------------------------- */\n" +
            "      @media all {\n" +
            "        .ExternalClass {\n" +
            "          width: 100%; }\n" +
            "        .ExternalClass,\n" +
            "        .ExternalClass p,\n" +
            "        .ExternalClass span,\n" +
            "        .ExternalClass font,\n" +
            "        .ExternalClass td,\n" +
            "        .ExternalClass div {\n" +
            "          line-height: 100%; }\n" +
            "        .apple-link a {\n" +
            "          color: inherit !important;\n" +
            "          font-family: inherit !important;\n" +
            "          font-size: inherit !important;\n" +
            "          font-weight: inherit !important;\n" +
            "          line-height: inherit !important;\n" +
            "          text-decoration: none !important; } \n" +
            "        .btn-primary table td:hover {\n" +
            "          background-color: #34495e !important; }\n" +
            "        .btn-primary a:hover {\n" +
            "          background-color: #34495e !important;\n" +
            "          border-color: #34495e !important; } }\n" +
            "\n" +
            "    </style>\n" +
            "  </head>\n" +
            "  <body class=\"\">\n" +
            "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\n" +
            "      <tr>\n" +
            "        <td>&nbsp;</td>\n" +
            "        <td class=\"container\">\n" +
            "          <div class=\"content\">\n" +
            "\n" +
            "            <!-- START CENTERED WHITE CONTAINER -->\n" +
            "            <table class=\"main\">\n" +
            "\n" +
            "              <!-- START MAIN CONTENT AREA -->\n" +
            "              <tr>\n" +
            "                <td class=\"wrapper\">\n" +
            "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "                    <tr>\n" +
            "                      <td>\n" +
            "                        <p>Hi there,</p>\n" +
            "                        <p>Thank you for registering.</p>\n" +
            "                        <p>Please click this button to validate your email.</p>\n" +
            "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\">\n" +
            "                          <tbody>\n" +
            "                            <tr>\n" +
            "                              <td align=\"left\">\n" +
            "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "                                  <tbody>\n" +
            "                                    <tr>\n" +
            "                                      <td> <a href=\"http://www.aquajmt.com\" target=\"_blank\">Validate Email</a> </td>\n" +
            "                                    </tr>\n" +
            "                                  </tbody>\n" +
            "                                </table>\n" +
            "                              </td>\n" +
            "                            </tr>\n" +
            "                          </tbody>\n" +
            "                        </table>\n" +
            "                        <p>You have successfully registered to AquaJMT!</p>\n" +
            "                        <p>Thank you.</p>\n" +
            "                      </td>\n" +
            "                    </tr>\n" +
            "                  </table>\n" +
            "                </td>\n" +
            "              </tr>\n" +
            "\n" +
            "              <!-- END MAIN CONTENT AREA -->\n" +
            "              </table>\n" +
            "\n" +
            "            <!-- START FOOTER -->\n" +
            "            <div class=\"footer\">\n" +
            "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "                <tr>\n" +
            "                  <td class=\"content-block\">\n" +
            "                  </td>\n" +
            "                </tr>\n" +
            "                <tr>\n" +
            "                  <td class=\"content-block powered-by\">\n" +
            "                    Powered by <a href=\"http://htmlemail.io\">HTMLemail</a>.\n" +
            "                  </td>\n" +
            "                </tr>\n" +
            "              </table>\n" +
            "            </div>\n" +
            "\n" +
            "            <!-- END FOOTER -->\n" +
            "            \n" +
            "<!-- END CENTERED WHITE CONTAINER --></div>\n" +
            "        </td>\n" +
            "        <td>&nbsp;</td>\n" +
            "      </tr>\n" +
            "    </table>\n" +
            "  </body>\n" +
            "</html>";


    private String emailResetPassword = "<!doctype html>\n" +
            "<html>\n" +
            "  <head>\n" +
            "    <meta name=\"viewport\" content=\"width=device-width\" />\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
            "    <title>Simple Transactional Email</title>\n" +
            "    <style>\n" +
            "      /* -------------------------------------\n" +
            "          GLOBAL RESETS\n" +
            "      ------------------------------------- */\n" +
            "      img {\n" +
            "        border: none;\n" +
            "        -ms-interpolation-mode: bicubic;\n" +
            "        max-width: 100%; }\n" +
            "\n" +
            "      body {\n" +
            "        background-color: #f6f6f6;\n" +
            "        font-family: sans-serif;\n" +
            "        -webkit-font-smoothing: antialiased;\n" +
            "        font-size: 14px;\n" +
            "        line-height: 1.4;\n" +
            "        margin: 0;\n" +
            "        padding: 0; \n" +
            "        -ms-text-size-adjust: 100%;\n" +
            "        -webkit-text-size-adjust: 100%; }\n" +
            "\n" +
            "      table {\n" +
            "        border-collapse: separate;\n" +
            "        mso-table-lspace: 0pt;\n" +
            "        mso-table-rspace: 0pt;\n" +
            "        width: 100%; }\n" +
            "        table td {\n" +
            "          font-family: sans-serif;\n" +
            "          font-size: 14px;\n" +
            "          vertical-align: top; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          BODY & CONTAINER\n" +
            "      ------------------------------------- */\n" +
            "\n" +
            "      .body {\n" +
            "        background-color: #f6f6f6;\n" +
            "        width: 100%; }\n" +
            "\n" +
            "      /* Set a max-width, and make it display as block so it will automatically stretch to that width, but will also shrink down on a phone or something */\n" +
            "      .container {\n" +
            "        display: block;\n" +
            "        Margin: 0 auto !important;\n" +
            "        /* makes it centered */\n" +
            "        max-width: 580px;\n" +
            "        padding: 10px;\n" +
            "        width: auto !important;\n" +
            "        width: 580px; }\n" +
            "\n" +
            "      /* This should also be a block element, so that it will fill 100% of the .container */\n" +
            "      .content {\n" +
            "        box-sizing: border-box;\n" +
            "        display: block;\n" +
            "        Margin: 0 auto;\n" +
            "        max-width: 580px;\n" +
            "        padding: 10px; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          HEADER, FOOTER, MAIN\n" +
            "      ------------------------------------- */\n" +
            "      .main {\n" +
            "        background: #fff;\n" +
            "        border-radius: 3px;\n" +
            "        width: 100%; }\n" +
            "\n" +
            "      .wrapper {\n" +
            "        box-sizing: border-box;\n" +
            "        padding: 20px; }\n" +
            "\n" +
            "      .footer {\n" +
            "        clear: both;\n" +
            "        padding-top: 10px;\n" +
            "        text-align: center;\n" +
            "        width: 100%; }\n" +
            "        .footer td,\n" +
            "        .footer p,\n" +
            "        .footer span,\n" +
            "        .footer a {\n" +
            "          color: #999999;\n" +
            "          font-size: 12px;\n" +
            "          text-align: center; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          TYPOGRAPHY\n" +
            "      ------------------------------------- */\n" +
            "      h1,\n" +
            "      h2,\n" +
            "      h3,\n" +
            "      h4 {\n" +
            "        color: #000000;\n" +
            "        font-family: sans-serif;\n" +
            "        font-weight: 400;\n" +
            "        line-height: 1.4;\n" +
            "        margin: 0;\n" +
            "        Margin-bottom: 30px; }\n" +
            "\n" +
            "      h1 {\n" +
            "        font-size: 35px;\n" +
            "        font-weight: 300;\n" +
            "        text-align: center;\n" +
            "        text-transform: capitalize; }\n" +
            "\n" +
            "      p,\n" +
            "      ul,\n" +
            "      ol {\n" +
            "        font-family: sans-serif;\n" +
            "        font-size: 14px;\n" +
            "        font-weight: normal;\n" +
            "        margin: 0;\n" +
            "        Margin-bottom: 15px; }\n" +
            "        p li,\n" +
            "        ul li,\n" +
            "        ol li {\n" +
            "          list-style-position: inside;\n" +
            "          margin-left: 5px; }\n" +
            "\n" +
            "      a {\n" +
            "        color: #3498db;\n" +
            "        text-decoration: underline; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          BUTTONS\n" +
            "      ------------------------------------- */\n" +
            "      .btn {\n" +
            "        box-sizing: border-box;\n" +
            "        width: 100%; }\n" +
            "        .btn > tbody > tr > td {\n" +
            "          padding-bottom: 15px; }\n" +
            "        .btn table {\n" +
            "          width: auto; }\n" +
            "        .btn table td {\n" +
            "          background-color: #ffffff;\n" +
            "          border-radius: 5px;\n" +
            "          text-align: center; }\n" +
            "        .btn a {\n" +
            "          background-color: #ffffff;\n" +
            "          border: solid 1px #3498db;\n" +
            "          border-radius: 5px;\n" +
            "          box-sizing: border-box;\n" +
            "          color: #3498db;\n" +
            "          cursor: pointer;\n" +
            "          display: inline-block;\n" +
            "          font-size: 14px;\n" +
            "          font-weight: bold;\n" +
            "          margin: 0;\n" +
            "          padding: 12px 25px;\n" +
            "          text-decoration: none;\n" +
            "          text-transform: capitalize; }\n" +
            "\n" +
            "      .btn-primary table td {\n" +
            "        background-color: #3498db; }\n" +
            "\n" +
            "      .btn-primary a {\n" +
            "        background-color: #3498db;\n" +
            "        border-color: #3498db;\n" +
            "        color: #ffffff; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          OTHER STYLES THAT MIGHT BE USEFUL\n" +
            "      ------------------------------------- */\n" +
            "      .last {\n" +
            "        margin-bottom: 0; }\n" +
            "\n" +
            "      .first {\n" +
            "        margin-top: 0; }\n" +
            "\n" +
            "      .align-center {\n" +
            "        text-align: center; }\n" +
            "\n" +
            "      .align-right {\n" +
            "        text-align: right; }\n" +
            "\n" +
            "      .align-left {\n" +
            "        text-align: left; }\n" +
            "\n" +
            "      .clear {\n" +
            "        clear: both; }\n" +
            "\n" +
            "      .mt0 {\n" +
            "        margin-top: 0; }\n" +
            "\n" +
            "      .mb0 {\n" +
            "        margin-bottom: 0; }\n" +
            "\n" +
            "      .preheader {\n" +
            "        color: transparent;\n" +
            "        display: none;\n" +
            "        height: 0;\n" +
            "        max-height: 0;\n" +
            "        max-width: 0;\n" +
            "        opacity: 0;\n" +
            "        overflow: hidden;\n" +
            "        mso-hide: all;\n" +
            "        visibility: hidden;\n" +
            "        width: 0; }\n" +
            "\n" +
            "      .powered-by a {\n" +
            "        text-decoration: none; }\n" +
            "\n" +
            "      hr {\n" +
            "        border: 0;\n" +
            "        border-bottom: 1px solid #f6f6f6;\n" +
            "        Margin: 20px 0; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
            "      ------------------------------------- */\n" +
            "      @media only screen and (max-width: 620px) {\n" +
            "        table[class=body] h1 {\n" +
            "          font-size: 28px !important;\n" +
            "          margin-bottom: 10px !important; }\n" +
            "        table[class=body] p,\n" +
            "        table[class=body] ul,\n" +
            "        table[class=body] ol,\n" +
            "        table[class=body] td,\n" +
            "        table[class=body] span,\n" +
            "        table[class=body] a {\n" +
            "          font-size: 16px !important; }\n" +
            "        table[class=body] .wrapper,\n" +
            "        table[class=body] .article {\n" +
            "          padding: 10px !important; }\n" +
            "        table[class=body] .content {\n" +
            "          padding: 0 !important; }\n" +
            "        table[class=body] .container {\n" +
            "          padding: 0 !important;\n" +
            "          width: 100% !important; }\n" +
            "        table[class=body] .main {\n" +
            "          border-left-width: 0 !important;\n" +
            "          border-radius: 0 !important;\n" +
            "          border-right-width: 0 !important; }\n" +
            "        table[class=body] .btn table {\n" +
            "          width: 100% !important; }\n" +
            "        table[class=body] .btn a {\n" +
            "          width: 100% !important; }\n" +
            "        table[class=body] .img-responsive {\n" +
            "          height: auto !important;\n" +
            "          max-width: 100% !important;\n" +
            "          width: auto !important; }}\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          PRESERVE THESE STYLES IN THE HEAD\n" +
            "      ------------------------------------- */\n" +
            "      @media all {\n" +
            "        .ExternalClass {\n" +
            "          width: 100%; }\n" +
            "        .ExternalClass,\n" +
            "        .ExternalClass p,\n" +
            "        .ExternalClass span,\n" +
            "        .ExternalClass font,\n" +
            "        .ExternalClass td,\n" +
            "        .ExternalClass div {\n" +
            "          line-height: 100%; }\n" +
            "        .apple-link a {\n" +
            "          color: inherit !important;\n" +
            "          font-family: inherit !important;\n" +
            "          font-size: inherit !important;\n" +
            "          font-weight: inherit !important;\n" +
            "          line-height: inherit !important;\n" +
            "          text-decoration: none !important; } \n" +
            "        .btn-primary table td:hover {\n" +
            "          background-color: #34495e !important; }\n" +
            "        .btn-primary a:hover {\n" +
            "          background-color: #34495e !important;\n" +
            "          border-color: #34495e !important; } }\n" +
            "\n" +
            "    </style>\n" +
            "  </head>\n" +
            "  <body class=\"\">\n" +
            "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\n" +
            "      <tr>\n" +
            "        <td>&nbsp;</td>\n" +
            "        <td class=\"container\">\n" +
            "          <div class=\"content\">\n" +
            "\n" +
            "            <!-- START CENTERED WHITE CONTAINER -->\n" +
            "            <table class=\"main\">\n" +
            "\n" +
            "              <!-- START MAIN CONTENT AREA -->\n" +
            "              <tr>\n" +
            "                <td class=\"wrapper\">\n" +
            "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "                    <tr>\n" +
            "                      <td>\n" +
            "                        <p>Dear Valued Customer,</p>\n" +
            "                        <p>We received your request to change your password.</p>\n" +
            "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\">\n" +
            "                          <tbody>\n" +
            "                            <tr>\n" +
            "                              <td align=\"left\">\n" +
            "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "                                  <tbody>\n" +
            "                                    <tr>\n" +
            "                                      <td>  Here is your Temporary Password: XXXXXXXXXXX </td>\n" +
            "                                    </tr>\n" +
            "                                    <tr>\n" +
            "                                      <td>  Thank you. </td>\n" +
            "                                    </tr>\n" +
            "                                  </tbody>\n" +
            "                                </table>\n" +
            "                              </td>\n" +
            "                            </tr>\n" +
            "                          </tbody>\n" +
            "                        </table>\n" +
            "                        <p>&nbsp;</p>\n" +
            "                        <p>&nbsp;</p>\n" +
            "                      </td>\n" +
            "                    </tr>\n" +
            "                  </table>\n" +
            "                </td>\n" +
            "              </tr>\n" +
            "\n" +
            "              <!-- END MAIN CONTENT AREA -->\n" +
            "              </table>\n" +
            "\n" +
            "            <!-- START FOOTER -->\n" +
            "            <div class=\"footer\">\n" +
            "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "                <tr>\n" +
            "                  <td class=\"content-block\">\n" +
            "                  </td>\n" +
            "                </tr>\n" +
            "                <tr>\n" +
            "                  <td class=\"content-block powered-by\">\n" +
            "                    Powered by <a href=\"http://htmlemail.io\">HTMLemail</a>.\n" +
            "                  </td>\n" +
            "                </tr>\n" +
            "              </table>\n" +
            "            </div>\n" +
            "\n" +
            "            <!-- END FOOTER -->\n" +
            "            \n" +
            "<!-- END CENTERED WHITE CONTAINER --></div>\n" +
            "        </td>\n" +
            "        <td>&nbsp;</td>\n" +
            "      </tr>\n" +
            "    </table>\n" +
            "  </body>\n" +
            "</html>";

    private String inpatientNotification = "<!doctype html>\n" +
            "<html>\n" +
            "  <head>\n" +
            "    <meta name=\"viewport\" content=\"width=device-width\" />\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
            "    <title>Simple Transactional Email</title>\n" +
            "    <style>\n" +
            "      /* -------------------------------------\n" +
            "          GLOBAL RESETS\n" +
            "      ------------------------------------- */\n" +
            "      img {\n" +
            "        border: none;\n" +
            "        -ms-interpolation-mode: bicubic;\n" +
            "        max-width: 100%; }\n" +
            "\n" +
            "      body {\n" +
            "        background-color: #f6f6f6;\n" +
            "        font-family: sans-serif;\n" +
            "        -webkit-font-smoothing: antialiased;\n" +
            "        font-size: 14px;\n" +
            "        line-height: 1.4;\n" +
            "        margin: 0;\n" +
            "        padding: 0; \n" +
            "        -ms-text-size-adjust: 100%;\n" +
            "        -webkit-text-size-adjust: 100%; }\n" +
            "\n" +
            "      table {\n" +
            "        border-collapse: separate;\n" +
            "        mso-table-lspace: 0pt;\n" +
            "        mso-table-rspace: 0pt;\n" +
            "        width: 100%; }\n" +
            "        table td {\n" +
            "          font-family: sans-serif;\n" +
            "          font-size: 14px;\n" +
            "          vertical-align: top; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          BODY & CONTAINER\n" +
            "      ------------------------------------- */\n" +
            "\n" +
            "      .body {\n" +
            "        background-color: #f6f6f6;\n" +
            "        width: 100%; }\n" +
            "\n" +
            "      /* Set a max-width, and make it display as block so it will automatically stretch to that width, but will also shrink down on a phone or something */\n" +
            "      .container {\n" +
            "        display: block;\n" +
            "        Margin: 0 auto !important;\n" +
            "        /* makes it centered */\n" +
            "        max-width: 580px;\n" +
            "        padding: 10px;\n" +
            "        width: auto !important;\n" +
            "        width: 580px; }\n" +
            "\n" +
            "      /* This should also be a block element, so that it will fill 100% of the .container */\n" +
            "      .content {\n" +
            "        box-sizing: border-box;\n" +
            "        display: block;\n" +
            "        Margin: 0 auto;\n" +
            "        max-width: 580px;\n" +
            "        padding: 10px; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          HEADER, FOOTER, MAIN\n" +
            "      ------------------------------------- */\n" +
            "      .main {\n" +
            "        background: #fff;\n" +
            "        border-radius: 3px;\n" +
            "        width: 100%; }\n" +
            "\n" +
            "      .wrapper {\n" +
            "        box-sizing: border-box;\n" +
            "        padding: 20px; }\n" +
            "\n" +
            "      .footer {\n" +
            "        clear: both;\n" +
            "        padding-top: 10px;\n" +
            "        text-align: center;\n" +
            "        width: 100%; }\n" +
            "        .footer td,\n" +
            "        .footer p,\n" +
            "        .footer span,\n" +
            "        .footer a {\n" +
            "          color: #999999;\n" +
            "          font-size: 12px;\n" +
            "          text-align: center; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          TYPOGRAPHY\n" +
            "      ------------------------------------- */\n" +
            "      h1,\n" +
            "      h2,\n" +
            "      h3,\n" +
            "      h4 {\n" +
            "        color: #000000;\n" +
            "        font-family: sans-serif;\n" +
            "        font-weight: 400;\n" +
            "        line-height: 1.4;\n" +
            "        margin: 0;\n" +
            "        Margin-bottom: 30px; }\n" +
            "\n" +
            "      h1 {\n" +
            "        font-size: 35px;\n" +
            "        font-weight: 300;\n" +
            "        text-align: center;\n" +
            "        text-transform: capitalize; }\n" +
            "\n" +
            "      p,\n" +
            "      ul,\n" +
            "      ol {\n" +
            "        font-family: sans-serif;\n" +
            "        font-size: 14px;\n" +
            "        font-weight: normal;\n" +
            "        margin: 0;\n" +
            "        Margin-bottom: 15px; }\n" +
            "        p li,\n" +
            "        ul li,\n" +
            "        ol li {\n" +
            "          list-style-position: inside;\n" +
            "          margin-left: 5px; }\n" +
            "\n" +
            "      a {\n" +
            "        color: #3498db;\n" +
            "        text-decoration: underline; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          BUTTONS\n" +
            "      ------------------------------------- */\n" +
            "      .btn {\n" +
            "        box-sizing: border-box;\n" +
            "        width: 100%; }\n" +
            "        .btn > tbody > tr > td {\n" +
            "          padding-bottom: 15px; }\n" +
            "        .btn table {\n" +
            "          width: auto; }\n" +
            "        .btn table td {\n" +
            "          background-color: #ffffff;\n" +
            "          border-radius: 5px;\n" +
            "          text-align: center; }\n" +
            "        .btn a {\n" +
            "          background-color: #ffffff;\n" +
            "          border: solid 1px #3498db;\n" +
            "          border-radius: 5px;\n" +
            "          box-sizing: border-box;\n" +
            "          color: #3498db;\n" +
            "          cursor: pointer;\n" +
            "          display: inline-block;\n" +
            "          font-size: 14px;\n" +
            "          font-weight: bold;\n" +
            "          margin: 0;\n" +
            "          padding: 12px 25px;\n" +
            "          text-decoration: none;\n" +
            "          text-transform: capitalize; }\n" +
            "\n" +
            "      .btn-primary table td {\n" +
            "        background-color: #3498db; }\n" +
            "\n" +
            "      .btn-primary a {\n" +
            "        background-color: #3498db;\n" +
            "        border-color: #3498db;\n" +
            "        color: #ffffff; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          OTHER STYLES THAT MIGHT BE USEFUL\n" +
            "      ------------------------------------- */\n" +
            "      .last {\n" +
            "        margin-bottom: 0; }\n" +
            "\n" +
            "      .first {\n" +
            "        margin-top: 0; }\n" +
            "\n" +
            "      .align-center {\n" +
            "        text-align: center; }\n" +
            "\n" +
            "      .align-right {\n" +
            "        text-align: right; }\n" +
            "\n" +
            "      .align-left {\n" +
            "        text-align: left; }\n" +
            "\n" +
            "      .clear {\n" +
            "        clear: both; }\n" +
            "\n" +
            "      .mt0 {\n" +
            "        margin-top: 0; }\n" +
            "\n" +
            "      .mb0 {\n" +
            "        margin-bottom: 0; }\n" +
            "\n" +
            "      .preheader {\n" +
            "        color: transparent;\n" +
            "        display: none;\n" +
            "        height: 0;\n" +
            "        max-height: 0;\n" +
            "        max-width: 0;\n" +
            "        opacity: 0;\n" +
            "        overflow: hidden;\n" +
            "        mso-hide: all;\n" +
            "        visibility: hidden;\n" +
            "        width: 0; }\n" +
            "\n" +
            "      .powered-by a {\n" +
            "        text-decoration: none; }\n" +
            "\n" +
            "      hr {\n" +
            "        border: 0;\n" +
            "        border-bottom: 1px solid #f6f6f6;\n" +
            "        Margin: 20px 0; }\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          RESPONSIVE AND MOBILE FRIENDLY STYLES\n" +
            "      ------------------------------------- */\n" +
            "      @media only screen and (max-width: 620px) {\n" +
            "        table[class=body] h1 {\n" +
            "          font-size: 28px !important;\n" +
            "          margin-bottom: 10px !important; }\n" +
            "        table[class=body] p,\n" +
            "        table[class=body] ul,\n" +
            "        table[class=body] ol,\n" +
            "        table[class=body] td,\n" +
            "        table[class=body] span,\n" +
            "        table[class=body] a {\n" +
            "          font-size: 16px !important; }\n" +
            "        table[class=body] .wrapper,\n" +
            "        table[class=body] .article {\n" +
            "          padding: 10px !important; }\n" +
            "        table[class=body] .content {\n" +
            "          padding: 0 !important; }\n" +
            "        table[class=body] .container {\n" +
            "          padding: 0 !important;\n" +
            "          width: 100% !important; }\n" +
            "        table[class=body] .main {\n" +
            "          border-left-width: 0 !important;\n" +
            "          border-radius: 0 !important;\n" +
            "          border-right-width: 0 !important; }\n" +
            "        table[class=body] .btn table {\n" +
            "          width: 100% !important; }\n" +
            "        table[class=body] .btn a {\n" +
            "          width: 100% !important; }\n" +
            "        table[class=body] .img-responsive {\n" +
            "          height: auto !important;\n" +
            "          max-width: 100% !important;\n" +
            "          width: auto !important; }}\n" +
            "\n" +
            "      /* -------------------------------------\n" +
            "          PRESERVE THESE STYLES IN THE HEAD\n" +
            "      ------------------------------------- */\n" +
            "      @media all {\n" +
            "        .ExternalClass {\n" +
            "          width: 100%; }\n" +
            "        .ExternalClass,\n" +
            "        .ExternalClass p,\n" +
            "        .ExternalClass span,\n" +
            "        .ExternalClass font,\n" +
            "        .ExternalClass td,\n" +
            "        .ExternalClass div {\n" +
            "          line-height: 100%; }\n" +
            "        .apple-link a {\n" +
            "          color: inherit !important;\n" +
            "          font-family: inherit !important;\n" +
            "          font-size: inherit !important;\n" +
            "          font-weight: inherit !important;\n" +
            "          line-height: inherit !important;\n" +
            "          text-decoration: none !important; } \n" +
            "        .btn-primary table td:hover {\n" +
            "          background-color: #34495e !important; }\n" +
            "        .btn-primary a:hover {\n" +
            "          background-color: #34495e !important;\n" +
            "          border-color: #34495e !important; } }\n" +
            "\n" +
            "    </style>\n" +
            "  </head>\n" +
            "  <body class=\"\">\n" +
            "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\n" +
            "      <tr>\n" +
            "        <td>&nbsp;</td>\n" +
            "        <td class=\"container\">\n" +
            "          <div class=\"content\">\n" +
            "\n" +
            "            <!-- START CENTERED WHITE CONTAINER -->\n" +
            "            <table class=\"main\">\n" +
            "\n" +
            "              <!-- START MAIN CONTENT AREA -->\n" +
            "              <tr>\n" +
            "                <td class=\"wrapper\">\n" +
            "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "                    <tr>\n" +
            "                      <td>\n" +
            "                        <p>Hi there,</p>\n" +
            "                        <p>This is an inpatient notification.</p>\n" +
            "                                <table border=\"1\" cellpadding=\"2\" cellspacing=\"2\">\n" +
            "                                  <tbody>\n" +

            "                                    <tr>\n" +
            "                                      <td> Member Code: </td>\n" +
            "                                      <td> XXXXXXXXXXX </td>\n" +
            "                                    </tr>\n" +

            "                                    <tr>\n" +
            "                                      <td> Member Name: </td>\n" +
            "                                      <td> YYYYYYmemberName </td>\n" +
            "                                    </tr>\n" +

            "                                    <tr>\n" +
            "                                      <td> Hospital Name: </td>\n" +
            "                                      <td> YYYYYYhospitalName </td>\n" +
            "                                    </tr>\n" +

            "                                    <tr>\n" +
            "                                      <td> Diagnosis: </td>\n" +
            "                                      <td> YYYYYYdiagnosis </td>\n" +
            "                                    </tr>\n" +

            "                                    <tr>\n" +
            "                                      <td> Procedure: </td>\n" +
            "                                      <td> YYYYYYprocedureDesc </td>\n" +
            "                                    </tr>\n" +


            "                                    <tr>\n" +
            "                                      <td> Doctor Name: </td>\n" +
            "                                      <td> YYYYYYdoctorName </td>\n" +
            "                                    </tr>\n" +


            "                                    <tr>\n" +
            "                                      <td> Room Number:  </td>\n" +
            "                                      <td> YYYYYYroomNumber </td>\n" +
            "                                    </tr>\n" +

            "                                    <tr>\n" +
            "                                      <td> Room Price: </td>\n" +
            "                                      <td> YYYYYYroomPrice </td>\n" +
            "                                    </tr>\n" +

            "                                    <tr>\n" +
            "                                      <td> Room Category: </td>\n" +
            "                                      <td> YYYYYYcategory </td>\n" +
            "                                    </tr>\n" +

            "                                    <tr>\n" +
            "                                      <td> Date Admitted: </td>\n" +
            "                                      <td> YYYYYYdateAdmitted </td>\n" +
            "                                    </tr>\n" +

            "                                  </tbody>\n" +
            "                                </table>\n" +
            "                      </td>\n" +
            "                    </tr>\n" +
            "                  </table>\n" +
            "                 <p>If you have other concerns, you may email YYYYYYuserEmail </p" +
            "                </td>\n" +
            "              </tr>\n" +
            "\n" +
            "              <!-- END MAIN CONTENT AREA -->\n" +
            "              </table>\n" +
            "\n" +
            "            <!-- START FOOTER -->\n" +
            "            <div class=\"footer\">\n" +
            "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "                <tr>\n" +
            "                  <td class=\"content-block\">\n" +
            "                    <span class=\"apple-link\">https://www.medicardphils.com/</span>\n" +
            "                  </td>\n" +
            "                </tr>\n" +
            "                <tr>\n" +
            "                  <td class=\"content-block powered-by\">\n" +
            "                    Powered by <a href=\"http://htmlemail.io\">HTMLemail</a>.\n" +
            "                  </td>\n" +
            "                </tr>\n" +
            "              </table>\n" +
            "            </div>\n" +
            "\n" +
            "            <!-- END FOOTER -->\n" +
            "            \n" +
            "<!-- END CENTERED WHITE CONTAINER --></div>\n" +
            "        </td>\n" +
            "        <td>&nbsp;</td>\n" +
            "      </tr>\n" +
            "    </table>\n" +
            "  </body>\n" +
            "</html>";


    private void send(String from, String to, String replyTo, String subject, String body, boolean isHtml) {
        logger.info("send");
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setReplyTo(replyTo);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(body, isHtml);
            logger.info("send", mail);
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            logger.info("send", e);
            e.printStackTrace();
        } finally {
            logger.info("finally");
        }

    }

    public void sendRegisterSuccess(String email) {
    }
}

