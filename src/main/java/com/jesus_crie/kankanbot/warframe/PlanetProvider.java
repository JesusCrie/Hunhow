package com.jesus_crie.kankanbot.warframe;

public class PlanetProvider {


    public enum Planets {
        MERCURY("Mercury", "http://vignette1.wikia.nocookie.net/warframe/images/4/41/Mercury.png/revision/latest?cb=20161016165617"),
        CERES("Ceres", "http://vignette3.wikia.nocookie.net/warframe/images/2/24/Ceres.png/revision/latest?cb=20161016192352"),
        URANUS("Uranus", "http://vignette1.wikia.nocookie.net/warframe/images/e/ec/Uranus.png/revision/latest?cb=20161016042202"),
        ERIS("Eris", "http://vignette2.wikia.nocookie.net/warframe/images/b/b3/Eris.png/revision/latest?cb=20161016204513"),
        SATURN("Saturn", "http://vignette3.wikia.nocookie.net/warframe/images/2/28/Saturn.png/revision/latest?cb=20161014034807"),
        PLUTO("Pluto", "http://vignette2.wikia.nocookie.net/warframe/images/3/35/Pluto.png/revision/latest?cb=20161016195904"),
        VENUS("Venus", "http://vignette2.wikia.nocookie.net/warframe/images/d/dc/Venus.png/revision/latest?cb=20161013035729"),
        EARTH("Earth", "http://vignette1.wikia.nocookie.net/warframe/images/1/1e/Earth.png/revision/latest?cb=20161016212227"),
        JUPITER("Jupiter", "http://vignette2.wikia.nocookie.net/warframe/images/6/68/Jupiter.png/revision/latest?cb=20161016193837"),
        MARS("Mars", "http://vignette2.wikia.nocookie.net/warframe/images/d/de/Mars.png/revision/latest?cb=20161016192350"),
        SEDNA("Sedna", "http://vignette3.wikia.nocookie.net/warframe/images/4/48/Sedna.png/revision/latest?cb=20161016202602"),
        NEPTUNE("Neptune", "http://vignette2.wikia.nocookie.net/warframe/images/1/15/Neptune.png/revision/latest?cb=20161016195815"),
        EUROPA("Europa", "http://vignette3.wikia.nocookie.net/warframe/images/6/63/Europa.png/revision/latest?cb=20161016193842"),
        PHOBOS("Phobos", "http://vignette2.wikia.nocookie.net/warframe/images/b/bf/Phobos.png/revision/latest?cb=20161016180047"),
        THEVOID("Void", "http://vignette4.wikia.nocookie.net/warframe/images/4/46/Void.png/revision/latest?cb=20161016211233");

        private String name;
        private String url;
        Planets(String n, String u) {
            name = n;
            url = u;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return name;
        }

        public static Planets forName(String name) {
            for (Planets p : Planets.values()) {
                if (p.toString().equalsIgnoreCase(name))
                    return p;
            }
            return Planets.THEVOID;
        }
    }
}
