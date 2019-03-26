@XmlSchema(
        namespace = "http://www.tidalsoftware.com/client/tesservlet",
        elementFormDefault = XmlNsForm.QUALIFIED,
        xmlns = {
                @XmlNs(prefix="tes", namespaceURI="http://www.tidalsoftware.com/client/tesservlet")
        }
)
package entities;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;