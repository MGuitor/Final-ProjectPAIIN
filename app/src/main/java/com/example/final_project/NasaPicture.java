package com.example.final_project;

public class NasaPicture {
    long id;
    String date;
    String explain;
    String media_type;
    String service_version;
    String title;
    String url;
    String hdurl;



    public NasaPicture(String title, long id, String date, String explain, String url, String hdurl, String media_type, String service_version) {
        this.date=date;
        this.explain=explain;
        this.media_type=media_type;
        this.id=id;
        this.service_version=service_version;
        this.title=title;
        this.url=url;
        this.hdurl=hdurl;

    }



    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public long getId() {
        return id;
    }

    public String getHdurl() {
        return hdurl;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getService_version() {
        return service_version;
    }

    public void setService_version(String service_version) {
        this.service_version = service_version;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}

/*   Sample
{"date":"2020-02-01",
 "explanation":"When leaving lunar orbit in February 1971, the crew of Apollo 14 watched this Earthrise from their command module Kitty Hawk. With Earth's sunlit crescent just peeking over the lunar horizon, the cratered terrain in the foreground is along the lunar farside. Of course, while orbiting the Moon, the crew could watch Earth rise and set, but the Earth hung stationary in the sky over Fra Mauro Base, their landing site on the lunar surface. Rock samples brought back by the Apollo 14 mission included a 20 pound rock nicknamed Big Bertha, later determined to contain a likely fragment of a meteorite from planet Earth.",
"hdurl":"https://apod.nasa.gov/apod/image/2002/AS14-71-9845v2wmktwtr4Jerry.jpg",
"media_type":"image",
 "service_version":"v1",
"title":"Apollo 14 Heads for Home",
 "url":"https://apod.nasa.gov/apod/image/2002/AS14-71-9845v2wmktwtr4Jerry1024.jpg"}

 */