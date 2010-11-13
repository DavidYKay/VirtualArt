from django.conf.urls.defaults import *
from piston.resource import Resource
from server.virtualart.api.handlers import SimpleArtHandler, ComplexArtHandler

simpleart_resource = Resource(SimpleArtHandler)
complexart_resource = Resource(ComplexArtHandler)

urlpatterns = patterns('',
    url(r'^simpleart/(?P<id>\d+)$', simpleart_resource),  
    url(r'^simpleart$', simpleart_resource),
    url(r'^complexart/(?P<id>\d+)$', complexart_resource),  
    url(r'^complexart$', complexart_resource),
)
      
