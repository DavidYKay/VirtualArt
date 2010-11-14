from piston.handler import BaseHandler
from virtualart.models import *
from emitters import *
import traceback
import random


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

    def create(self, request, *args, **kwds):
        try:    
            print request.POST
            print request.FILES
            print request.FILES.items()
            host = request.get_host()            
            fields = {
                'name': request.POST['name'],
                'latitude': float(request.POST['latitude']),
                'longitude': float(request.POST['longitude']),
                'elevation': float(request.POST['elevation']),
                'direction': float(request.POST['direction']),
                'pitch': float(request.POST['pitch']),
            }
            fname,ext = request.FILES['image']._get_name().split('.')
            request.FILES['image']._set_name(str(random.getrandbits(32)) + "." + ext)
            fields['image'] = request.FILES['image']
            if id in request.POST and Art.objects.filter(id=id).exists():
                art = Art.objects.get(id=id)
                art.update(**fields)
            else:
                art = Art(**fields)
                art.save()
            #return [art.get_data(host)]
            return "<textarea>test</textarea>" 
        except:
            return traceback.format_exc()


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
                                            

