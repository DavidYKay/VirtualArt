from django.contrib import admin
from virtualart.models import *

class ArtAdmin(admin.ModelAdmin):
    list_display = ('name', 'latitude', 'longitude',
        'direction', 'pitch', 'elevation', 'image')
    

admin.site.register(Art, ArtAdmin)
