from django.conf.urls.defaults import *
from piston.resource import Resource
from server.virtualart.api.handlers import *

simpleart_resource = Resource(SimpleArtHandler)
complexart_resource = Resource(ComplexArtHandler)
geo_resource = Resource(GeoHandler)

urlpatterns = patterns('',
    url(r'^complexart/(?P<lat>\d+\.\d+)/(?P<lon>\d+\.\d+)/(?P<range>\d+\.\d+)$', geo_resource),
    url(r'^simpleart/(?P<id>\d+)$', simpleart_resource),  
    url(r'^simpleart$', simpleart_resource),
    url(r'^complexart/(?P<id>\d+)$', complexart_resource),  
    url(r'^complexart$', complexart_resource),
    url(r'^test', test_resource), 
)
      
