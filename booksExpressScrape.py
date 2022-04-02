from bs4 import BeautifulSoup
import requests
import json
class announcement:
    def __init__(self,title,price,link):
        self.title=title
        self.price=price
        self.link=link
    def get_link(self):
        return self.link

    def get_title(self):
        return self.title

    def get_price(self):
        return self.price
def scrapeBooksExpress():
    cod_watchshop = requests.get('https://www.books-express.ro/carti/fictiune/f').text
    html_code = BeautifulSoup(cod_watchshop,'lxml')
    data_storage = []
    for anunt in html_code.findAll(class_="vsep"):
       try:
            items = {}
            link = 'www.books-express.ro'+ anunt.find('a',class_="cover")['href']
            title = anunt.find('div',class_="narrow").a['title']
            price = anunt.find('span',class_='color-theme-5').text
            package = announcement(title,price,link)
            items["Title"] = package.title
            items["Price"] = package.price
            items["Link"] = package.link
            print(items)
            data_storage.append(items)

       except:
           pass
    print("\n----------------------------------------------")


    with open('scrapedBooksExpress.json','w') as output:
        json.dump(data_storage,output)