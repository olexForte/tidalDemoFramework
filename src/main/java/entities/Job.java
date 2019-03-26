package entities;

import io.restassured.response.Response;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Job extends BaseEntity{

    //region Fields, Getters and Setters
    String active;
    Integer agentid;
    Integer agentlistid;
    Integer agentlistostype;
    Integer agentlistserviceid;
    Integer agentostype;
    Integer agentserviceid;
    Integer agenttype;
    String alias;
    String allowrerun;
    String allowunscheduled;
    String alternateoutputfile;
    Integer businessunitid;
    Date cachelastchangetime;
    Date calendarfromDate;
    String calendarfromDateasString;
    Integer calendarid;
    Short calendaroffset;
    Date calendaruntilDate;
    String calendaruntilDateasString;
    Integer childrencount;
    Date clientcachelastchangetime;
    String command;
    Short concurrency;
    Date createtime;
    Short dependencylogic;
    String dirty;
    Short disablecarryover;
    Integer durationestimated;
    Integer durationmaximum;
    Integer durationminimum;
    String environmentfile;
    Short estimatedmethod;
    Integer excludeabnormalduration;
    Short exitcodenormaloperator;
    String fullpath;
    String hasparent;
    String predecessorsvalue;
    String successorsvalue;
    Short historyretention;
    Integer id;
    Integer imageid;
    Integer imagelinkid;
    Date imagelumt;
    String imagetype;
    String inheritagent;
    String inheritcalendar;
    String inheritoptions;
    String inheritrepeat;
    String inherittimewindow;
    Integer jobclassid;
    Integer jobclassimageid;
    Date jobclassimagelumt;
    String jobclassimagetype;
    String jobdeplist;
    Integer jobdetailid;
    Date lastchangetime;
    Date lastusermodifiedtime;
    String name;
    Short nearoutage;
    Short normalexitfromrange;
    Short normalexittorange;
    String notes;
    Boolean isnotes;
    Integer ownerid;
    String parameters;
    String parentactive;
    Integer parentid;
    String parentname;
    Short priority;
    String recordname;
    String repeat;
    Short repeatcount;
    Short repeatinterval;
    String rerundependency;
    String runbook;
    Boolean isrunbook;
    Integer runuserid;
    String saveoutputoption;
    Integer serviceid;
    Date timewindowfromtime;
    String timewindowfromtimeasString;
    Short timewindowoption;
    Date timewindowuntiltime;
    String timewindowuntiltimeasString;
    String trackingcommand;
    Short trackingmethod;
    Short type;
    Short unixprofile;
    String variables;
    String waitOperator;
    String workingdirectory;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Integer getAgentid() {
        return agentid;
    }

    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
    }

    public Integer getAgentlistid() {
        return agentlistid;
    }

    public void setAgentlistid(Integer agentlistid) {
        this.agentlistid = agentlistid;
    }

    public Integer getAgentlistostype() {
        return agentlistostype;
    }

    public void setAgentlistostype(Integer agentlistostype) {
        this.agentlistostype = agentlistostype;
    }

    public Integer getAgentlistserviceid() {
        return agentlistserviceid;
    }

    public void setAgentlistserviceid(Integer agentlistserviceid) {
        this.agentlistserviceid = agentlistserviceid;
    }

    public Integer getAgentostype() {
        return agentostype;
    }

    public void setAgentostype(Integer agentostype) {
        this.agentostype = agentostype;
    }

    public Integer getAgentserviceid() {
        return agentserviceid;
    }

    public void setAgentserviceid(Integer agentserviceid) {
        this.agentserviceid = agentserviceid;
    }

    public Integer getAgenttype() {
        return agenttype;
    }

    public void setAgenttype(Integer agenttype) {
        this.agenttype = agenttype;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAllowrerun() {
        return allowrerun;
    }

    public void setAllowrerun(String allowrerun) {
        this.allowrerun = allowrerun;
    }

    public String getAllowunscheduled() {
        return allowunscheduled;
    }

    public void setAllowunscheduled(String allowunscheduled) {
        this.allowunscheduled = allowunscheduled;
    }

    public String getAlternateoutputfile() {
        return alternateoutputfile;
    }

    public void setAlternateoutputfile(String alternateoutputfile) {
        this.alternateoutputfile = alternateoutputfile;
    }

    public Integer getBusinessunitid() {
        return businessunitid;
    }

    public void setBusinessunitid(Integer businessunitid) {
        this.businessunitid = businessunitid;
    }

    public Date getCachelastchangetime() {
        return cachelastchangetime;
    }

    public void setCachelastchangetime(Date cachelastchangetime) {
        this.cachelastchangetime = cachelastchangetime;
    }

    public Date getCalendarfromDate() {
        return calendarfromDate;
    }

    public void setCalendarfromDate(Date calendarfromDate) {
        this.calendarfromDate = calendarfromDate;
    }

    public String getCalendarfromDateasString() {
        return calendarfromDateasString;
    }

    public void setCalendarfromDateasString(String calendarfromDateasString) {
        this.calendarfromDateasString = calendarfromDateasString;
    }

    public Integer getCalendarid() {
        return calendarid;
    }

    public void setCalendarid(Integer calendarid) {
        this.calendarid = calendarid;
    }

    public Short getCalendaroffset() {
        return calendaroffset;
    }

    public void setCalendaroffset(Short calendaroffset) {
        this.calendaroffset = calendaroffset;
    }

    public Date getCalendaruntilDate() {
        return calendaruntilDate;
    }

    public void setCalendaruntilDate(Date calendaruntilDate) {
        this.calendaruntilDate = calendaruntilDate;
    }

    public String getCalendaruntilDateasString() {
        return calendaruntilDateasString;
    }

    public void setCalendaruntilDateasString(String calendaruntilDateasString) {
        this.calendaruntilDateasString = calendaruntilDateasString;
    }

    public Integer getChildrencount() {
        return childrencount;
    }

    public void setChildrencount(Integer childrencount) {
        this.childrencount = childrencount;
    }

    public Date getClientcachelastchangetime() {
        return clientcachelastchangetime;
    }

    public void setClientcachelastchangetime(Date clientcachelastchangetime) {
        this.clientcachelastchangetime = clientcachelastchangetime;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Short getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(Short concurrency) {
        this.concurrency = concurrency;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Short getDependencylogic() {
        return dependencylogic;
    }

    public void setDependencylogic(Short dependencylogic) {
        this.dependencylogic = dependencylogic;
    }

    public String getDirty() {
        return dirty;
    }

    public void setDirty(String dirty) {
        this.dirty = dirty;
    }

    public Short getDisablecarryover() {
        return disablecarryover;
    }

    public void setDisablecarryover(Short disablecarryover) {
        this.disablecarryover = disablecarryover;
    }

    public Integer getDurationestimated() {
        return durationestimated;
    }

    public void setDurationestimated(Integer durationestimated) {
        this.durationestimated = durationestimated;
    }

    public Integer getDurationmaximum() {
        return durationmaximum;
    }

    public void setDurationmaximum(Integer durationmaximum) {
        this.durationmaximum = durationmaximum;
    }

    public Integer getDurationminimum() {
        return durationminimum;
    }

    public void setDurationminimum(Integer durationminimum) {
        this.durationminimum = durationminimum;
    }

    public String getEnvironmentfile() {
        return environmentfile;
    }

    public void setEnvironmentfile(String environmentfile) {
        this.environmentfile = environmentfile;
    }

    public Short getEstimatedmethod() {
        return estimatedmethod;
    }

    public void setEstimatedmethod(Short estimatedmethod) {
        this.estimatedmethod = estimatedmethod;
    }

    public Integer getExcludeabnormalduration() {
        return excludeabnormalduration;
    }

    public void setExcludeabnormalduration(Integer excludeabnormalduration) {
        this.excludeabnormalduration = excludeabnormalduration;
    }

    public Short getExitcodenormaloperator() {
        return exitcodenormaloperator;
    }

    public void setExitcodenormaloperator(Short exitcodenormaloperator) {
        this.exitcodenormaloperator = exitcodenormaloperator;
    }

    public String getFullpath() {
        return fullpath;
    }

    public void setFullpath(String fullpath) {
        this.fullpath = fullpath;
    }

    public String getHasparent() {
        return hasparent;
    }

    public void setHasparent(String hasparent) {
        this.hasparent = hasparent;
    }

    public String getPredecessorsvalue() {
        return predecessorsvalue;
    }

    public void setPredecessorsvalue(String predecessorsvalue) {
        this.predecessorsvalue = predecessorsvalue;
    }

    public String getSuccessorsvalue() {
        return successorsvalue;
    }

    public void setSuccessorsvalue(String successorsvalue) {
        this.successorsvalue = successorsvalue;
    }

    public Short getHistoryretention() {
        return historyretention;
    }

    public void setHistoryretention(Short historyretention) {
        this.historyretention = historyretention;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImageid() {
        return imageid;
    }

    public void setImageid(Integer imageid) {
        this.imageid = imageid;
    }

    public Integer getImagelinkid() {
        return imagelinkid;
    }

    public void setImagelinkid(Integer imagelinkid) {
        this.imagelinkid = imagelinkid;
    }

    public Date getImagelumt() {
        return imagelumt;
    }

    public void setImagelumt(Date imagelumt) {
        this.imagelumt = imagelumt;
    }

    public String getImagetype() {
        return imagetype;
    }

    public void setImagetype(String imagetype) {
        this.imagetype = imagetype;
    }

    public String getInheritagent() {
        return inheritagent;
    }

    public void setInheritagent(String inheritagent) {
        this.inheritagent = inheritagent;
    }

    public String getInheritcalendar() {
        return inheritcalendar;
    }

    public void setInheritcalendar(String inheritcalendar) {
        this.inheritcalendar = inheritcalendar;
    }

    public String getInheritoptions() {
        return inheritoptions;
    }

    public void setInheritoptions(String inheritoptions) {
        this.inheritoptions = inheritoptions;
    }

    public String getInheritrepeat() {
        return inheritrepeat;
    }

    public void setInheritrepeat(String inheritrepeat) {
        this.inheritrepeat = inheritrepeat;
    }

    public String getInherittimewindow() {
        return inherittimewindow;
    }

    public void setInherittimewindow(String inherittimewindow) {
        this.inherittimewindow = inherittimewindow;
    }

    public Integer getJobclassid() {
        return jobclassid;
    }

    public void setJobclassid(Integer jobclassid) {
        this.jobclassid = jobclassid;
    }

    public Integer getJobclassimageid() {
        return jobclassimageid;
    }

    public void setJobclassimageid(Integer jobclassimageid) {
        this.jobclassimageid = jobclassimageid;
    }

    public Date getJobclassimagelumt() {
        return jobclassimagelumt;
    }

    public void setJobclassimagelumt(Date jobclassimagelumt) {
        this.jobclassimagelumt = jobclassimagelumt;
    }

    public String getJobclassimagetype() {
        return jobclassimagetype;
    }

    public void setJobclassimagetype(String jobclassimagetype) {
        this.jobclassimagetype = jobclassimagetype;
    }

    public String getJobdeplist() {
        return jobdeplist;
    }

    public void setJobdeplist(String jobdeplist) {
        this.jobdeplist = jobdeplist;
    }

    public Integer getJobdetailid() {
        return jobdetailid;
    }

    public void setJobdetailid(Integer jobdetailid) {
        this.jobdetailid = jobdetailid;
    }

    public Date getLastchangetime() {
        return lastchangetime;
    }

    public void setLastchangetime(Date lastchangetime) {
        this.lastchangetime = lastchangetime;
    }

    public Date getLastusermodifiedtime() {
        return lastusermodifiedtime;
    }

    public void setLastusermodifiedtime(Date lastusermodifiedtime) {
        this.lastusermodifiedtime = lastusermodifiedtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getNearoutage() {
        return nearoutage;
    }

    public void setNearoutage(Short nearoutage) {
        this.nearoutage = nearoutage;
    }

    public Short getNormalexitfromrange() {
        return normalexitfromrange;
    }

    public void setNormalexitfromrange(Short normalexitfromrange) {
        this.normalexitfromrange = normalexitfromrange;
    }

    public Short getNormalexittorange() {
        return normalexittorange;
    }

    public void setNormalexittorange(Short normalexittorange) {
        this.normalexittorange = normalexittorange;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getIsnotes() {
        return isnotes;
    }

    public void setIsnotes(Boolean isnotes) {
        this.isnotes = isnotes;
    }

    public Integer getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(Integer ownerid) {
        this.ownerid = ownerid;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getParentactive() {
        return parentactive;
    }

    public void setParentactive(String parentactive) {
        this.parentactive = parentactive;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public Short getPriority() {
        return priority;
    }

    public void setPriority(Short priority) {
        this.priority = priority;
    }

    public String getRecordname() {
        return recordname;
    }

    public void setRecordname(String recordname) {
        this.recordname = recordname;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public Short getRepeatcount() {
        return repeatcount;
    }

    public void setRepeatcount(Short repeatcount) {
        this.repeatcount = repeatcount;
    }

    public Short getRepeatinterval() {
        return repeatinterval;
    }

    public void setRepeatinterval(Short repeatinterval) {
        this.repeatinterval = repeatinterval;
    }

    public String getRerundependency() {
        return rerundependency;
    }

    public void setRerundependency(String rerundependency) {
        this.rerundependency = rerundependency;
    }

    public String getRunbook() {
        return runbook;
    }

    public void setRunbook(String runbook) {
        this.runbook = runbook;
    }

    public Boolean getIsrunbook() {
        return isrunbook;
    }

    public void setIsrunbook(Boolean isrunbook) {
        this.isrunbook = isrunbook;
    }

    public Integer getRunuserid() {
        return runuserid;
    }

    public void setRunuserid(Integer runuserid) {
        this.runuserid = runuserid;
    }

    public String getSaveoutputoption() {
        return saveoutputoption;
    }

    public void setSaveoutputoption(String saveoutputoption) {
        this.saveoutputoption = saveoutputoption;
    }

    public Integer getServiceid() {
        return serviceid;
    }

    public void setServiceid(Integer serviceid) {
        this.serviceid = serviceid;
    }

    public Date getTimewindowfromtime() {
        return timewindowfromtime;
    }

    public void setTimewindowfromtime(Date timewindowfromtime) {
        this.timewindowfromtime = timewindowfromtime;
    }

    public String getTimewindowfromtimeasString() {
        return timewindowfromtimeasString;
    }

    public void setTimewindowfromtimeasString(String timewindowfromtimeasString) {
        this.timewindowfromtimeasString = timewindowfromtimeasString;
    }

    public Short getTimewindowoption() {
        return timewindowoption;
    }

    public void setTimewindowoption(Short timewindowoption) {
        this.timewindowoption = timewindowoption;
    }

    public Date getTimewindowuntiltime() {
        return timewindowuntiltime;
    }

    public void setTimewindowuntiltime(Date timewindowuntiltime) {
        this.timewindowuntiltime = timewindowuntiltime;
    }

    public String getTimewindowuntiltimeasString() {
        return timewindowuntiltimeasString;
    }

    public void setTimewindowuntiltimeasString(String timewindowuntiltimeasString) {
        this.timewindowuntiltimeasString = timewindowuntiltimeasString;
    }

    public String getTrackingcommand() {
        return trackingcommand;
    }

    public void setTrackingcommand(String trackingcommand) {
        this.trackingcommand = trackingcommand;
    }

    public Short getTrackingmethod() {
        return trackingmethod;
    }

    public void setTrackingmethod(Short trackingmethod) {
        this.trackingmethod = trackingmethod;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getUnixprofile() {
        return unixprofile;
    }

    public void setUnixprofile(Short unixprofile) {
        this.unixprofile = unixprofile;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public String getWaitOperator() {
        return waitOperator;
    }

    public void setWaitOperator(String waitOperator) {
        this.waitOperator = waitOperator;
    }

    public String getWorkingdirectory() {
        return workingdirectory;
    }

    public void setWorkingdirectory(String workingdirectory) {
        this.workingdirectory = workingdirectory;
    }
    //endregion

    //region Conversions
    public static Job getJobFromFile(String filePath) throws Exception {
        Document doc = getDocumentBuilder().parse(new File(filePath));
        return (Job)getUnmarshaller(Job.class).unmarshal(doc);
    }

    public static List<Job> getJobsFromResponse(Response response) throws Exception {
        InputStream responseBodyIS = response.body().asInputStream();
        Document doc = getDocumentBuilder().parse(responseBodyIS);

        XPath xPath =  XPathFactory.newInstance().newXPath();
        NodeList jobsNodeList = ((NodeList)xPath.compile("//*[local-name() = 'job']").evaluate(doc, XPathConstants.NODESET));

        List<Job> jobs = new ArrayList<Job>();

        for(int i = 0; i < jobsNodeList.getLength(); i++){
            Job job = (Job)getUnmarshaller(Job.class).unmarshal(jobsNodeList.item(i));
            jobs.add(job);
        }

        return jobs;
    }

    public static String getJobIdFromResponse(Response response) throws Exception{
        InputStream responseBodyIS = response.body().asInputStream();
        Document doc = getDocumentBuilder().parse(responseBodyIS);

        XPath xPath =  XPathFactory.newInstance().newXPath();
        return xPath.compile("//*[local-name() = 'objectid']").evaluate(doc);
    }
    //endregion

    //region Requests
    public String createRequest() throws JAXBException {

        OutputStream marshalledJobOS = new ByteArrayOutputStream();
        getMarshaller(Job.class).marshal(this, marshalledJobOS);
        String marshalledJob = marshalledJobOS.toString().replaceAll("<\\?.+\\?>\n","");

        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<entry xmlns=\"http://purl.org/atom/ns#\">\n" +
                "\t<tes:Job.create xmlns:tes=\"http://www.tidalsoftware.com/client/tesservlet\">\n" +
                marshalledJob +
                "\n" +
                "\t</tes:Job.create>\n" +
                "</entry>";
    }

    public String deleteRequest() {

        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<entry xmlns=\"http://purl.org/atom/ns#\">\n" +
                "\t<tes:Job.delete xmlns:tes=\"http://www.tidalsoftware.com/client/tesservlet\">\n" +
                "\t\t<object>" +
                "<tes:id>" + this.getId() + "</tes:id>" +
                "</object>\n" +
                "\t</tes:Job.delete>\n" +
                "</entry>";
    }

    public static String deleteRequest(String id) {

        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<entry xmlns=\"http://purl.org/atom/ns#\">\n" +
                "\t<tes:Job.delete xmlns:tes=\"http://www.tidalsoftware.com/client/tesservlet\">\n" +
                "\t\t<object>" +
                "<tes:id>" + id + "</tes:id>" +
                "</object>\n" +
                "\t</tes:Job.delete>\n" +
                "</entry>";
    }

    public static String getJobRequest(String id){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<entry xmlns=\"http://purl.org/atom/ns#\">\n" +
                "\t<tes:Job.get xmlns:tes=\"http://www.tidalsoftware.com/client/tesservlet\">\n" +
                "\t\t<id>" +
                id +
                "</id>\n" +
                "\t\t<selectColumns></selectColumns>\n" +
                "\t\t<queryCondition></queryCondition>\n" +
                "\t</tes:Job.get>\n" +
                "</entry>";
    }
    //endregion
}
