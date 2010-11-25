from piston.handler import BaseHandler
from virtualart.models import *


class SimpleArtHandler(BaseHandler):
    allowed_methods = ('GET', 'POST', 'DELETE', 'PUT')
    model = SimpleArt
    def read(self, request, id=None):
        if id:
            return SimpleArt.objects.filter(id=id)
        else:
            return SimpleArt.objects.all()


class GeoHandler(BaseHandler):
    model = ComplexArt
    def read(self, request, lat, lon, range):
        print type(lat), type(lon), type(range)
        print lat,lon,range 
        lat = float(lat)
        lon = float(lon)
        range = float(range)
        in_range = []      
        for art in ComplexArt.objects.all():
            if (art.longitude-lon)**2 + (art.latitude-lat)**2 < range**2:
                in_range.append(art)
        return in_range 
                               
                
class ComplexArtHandler(BaseHandler):
    allowed_methods = ('GET', 'POST', 'DELETE', 'PUT')
    model = ComplexArt


