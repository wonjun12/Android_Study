package com.wonjun.training14_googleapi_study.model;

import java.io.Serializable;

public class Place implements Serializable {
    private PlaceLocation geometry;
    private String name;
    private String vicinity;

    public class PlaceLocation implements Serializable {
        private PlaceLocationValue location;

        public class PlaceLocationValue implements Serializable {
            double lat;
            double lng;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }

        public PlaceLocationValue getLocation() {
            return location;
        }

        public void setLocation(PlaceLocationValue location) {
            this.location = location;
        }
    }

    public PlaceLocation getGeometry() {
        return geometry;
    }

    public void setGeometry(PlaceLocation geometry) {
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
