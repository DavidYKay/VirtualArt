from piston.handler import BaseHandler
from virtualart.models import *
from emitters import *

class ArtHandler(BaseHandler):
    allowed_methods = ('GET', 'POST', 'DELETE', 'PUT')
    model = Art
    def read(self, request, id=None):
#        print dir(request)
#        print request
#        print request.build_absolute_uri()
#        print request.get_host()
        host = request.get_host()
        if id:
            return [x.get_data(host) for x in Art.objects.filter(id=id)]
        else:
            return [x.get_data(host) for x in Art.objects.all()]


class GeoArtHandler(BaseHandler):
    model = Art
    def read(self, request, lat, lon, range):
        print type(lat), type(lon), type(range)
        print lat,lon,range 
        host = request.get_host()
        lat = float(lat)
        lon = float(lon)
        range = float(range)
        in_range = []
        for art in Art.objects.all():
            if (art.longitude-lon)**2 + (art.latitude-lat)**2 < range**2:
                in_range.append(art.get_data(host))
        return in_range 
                                            

