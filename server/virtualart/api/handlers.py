from piston.handler import BaseHandler
from virtualart.models import *


class ArtHandler(BaseHandler):
    allowed_methods = ('GET', 'POST', 'DELETE', 'PUT')
    model = Art
    def read(self, request, id=None):
        if id:
            return Art.objects.filter(id=id)
        else:
            return Art.objects.all()


class GeoArtHandler(BaseHandler):
    model = Art
    def read(self, request, lat, lon, range):
        print type(lat), type(lon), type(range)
        print lat,lon,range 
        lat = float(lat)
        lon = float(lon)
        range = float(range)
        in_range = []      
        for art in Art.objects.all():
            if (art.longitude-lon)**2 + (art.latitude-lat)**2 < range**2:
                in_range.append(art)
        return in_range 
                                            

